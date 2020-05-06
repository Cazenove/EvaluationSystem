package com.evaluation.system.service;

import com.evaluation.system.bean.Class;
import com.evaluation.system.bean.EvaluationInner;
import com.evaluation.system.bean.EvaluationOuter;
import com.evaluation.system.bean.SubmitOuter;
import com.evaluation.system.config.PublishTimedTask;
import com.evaluation.system.controller.EvaluationOuterController;
import com.evaluation.system.dao.ClassRepository;
import com.evaluation.system.dao.EvaluationInnerRepository;
import com.evaluation.system.dao.EvaluationOuterRepository;
import com.evaluation.system.dao.SubmitOuterRepository;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.transaction.Transaction;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 22170128 chh
 */
@Service
public class EvaluationOuterService {

    @Autowired
    private EvaluationOuterRepository evaluationOuterRepository;

    @Autowired
    private EvaluationInnerRepository evaluationInnerRepository;

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private SubmitOuterRepository submitOuterRepository;

    @Autowired
    private PublishTimedTask publishTimedTask;



    @Transactional(rollbackFor = Exception.class)
    public Map<String,Object> saveEvaluationOuter(EvaluationOuter evaluationOuter,int flag)
    {
        Map<String,Object> result =new HashMap<>();
        result.put("status","0");
        result.put("msg","班级不存在");
        try {
            EvaluationInner evaluationInner;
            if(flag!=1)
            {
                evaluationInner = evaluationInnerRepository.findOneByEvaluationInnerId(evaluationOuter.getEvaluationOuterId());
            }
            else
            {
                evaluationInner = new EvaluationInner();
            }
            Class c = classRepository.findByClassId(evaluationOuter.getClassId());
            if(c == null) {
                return result;
            }
            evaluationInner.setClassInfo(c);
            evaluationInner.setContent(evaluationOuter.getContent());
            evaluationInner.setName(evaluationOuter.getName() + "_组内评分表");
            evaluationInner.setReleaseTime(evaluationOuter.getReleaseTime());
            evaluationInner.setEndTime(evaluationOuter.getEndTime());
            evaluationOuter.setClassInfo(c);
            evaluationOuter.setName(evaluationOuter.getName()+"_组间评分表");
            EvaluationOuter e = evaluationOuterRepository.save(evaluationOuter);
            evaluationInner.setEvaluationInnerId(e.getEvaluationOuterId());
            evaluationInnerRepository.save(evaluationInner);
            if(flag==1){

                long endTime = Long.parseLong(e.getEndTime()) * 1000;
                Integer id = e.getEvaluationOuterId();
                if(System.currentTimeMillis() < endTime)
                {
                    publishTimedTask.publishTask(getCronString(endTime),System.currentTimeMillis(),endTime,
                            id.toString(),id.toString());
                }
                result.put("status","1");
                result.put("msg","创建成功");
            }
            else{

                long endTime = Long.parseLong(e.getEndTime()) * 1000;
                Integer id = e.getEvaluationOuterId();
                if(System.currentTimeMillis() < endTime)
                {
                    TriggerKey triggerKey = new TriggerKey("trigger-" + id.toString(), "triggerGroup-" + id.toString());
                    if (publishTimedTask.getScheduler().getTrigger(triggerKey)==null)
                    {
                        publishTimedTask.publishTask(getCronString(endTime),System.currentTimeMillis(),endTime,
                                id.toString(),id.toString());
                    }
                    else{
                        publishTimedTask.changeTaskTime(id.toString(),endTime,id.toString());
                    }
                }
                result.put("status","1");
                result.put("msg","修改成功");
            }

        }
        catch (Exception e)
        {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            if(flag==1){
                result.put("status","0");
                result.put("msg","创建失败，请检查是否重名或填写是否规范");
            }
            else{
                result.put("status","0");
                result.put("msg","修改失败，请检查是否重名或填写是否规范");
            }
            return result;
        }

        return result;
    }

    public Map<String,Object> findEvaluationOuter()
    {
        Map<String,Object> result =new HashMap<>();
        try {
            List<EvaluationOuter> e = evaluationOuterRepository.findAll();
            result.put("status","1");
            result.put("data",e);

        }
        catch (Exception e)
        {
            System.out.println(e.toString());
            result.put("status","0");
            result.put("msg","查找出错");
        }

        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    public Map<String,Object> deleteEvaluationOuter(EvaluationOuter evaluationOuter)
    {
        Integer id = evaluationOuter.getEvaluationOuterId();
        EvaluationOuter e = evaluationOuterRepository.findOneByEvaluationOuterId(id);
        Map<String,Object> result =new HashMap<>();
        try {
            evaluationOuterRepository.deleteById(id);
            evaluationInnerRepository.deleteById(id);
            long endTime = Long.parseLong(e.getEndTime()) * 1000;
            if(System.currentTimeMillis() < endTime)
            {
                TriggerKey triggerKey = new TriggerKey("trigger-" + id.toString(), "triggerGroup-" + id.toString());
                if(publishTimedTask.getScheduler().getTrigger(triggerKey)!=null){
                    publishTimedTask.deleteTaskTime(id.toString());
                }

            }
            result.put("status","1");
            result.put("msg","删除成功");
        }
        catch (Exception ex){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            result.put("status","0");
            result.put("msg","删除失败");
        }
        return result;
    }


    public String getCronString(long timeStamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(sdf.format(new Date()));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sd = simpleDateFormat.format(new Date(timeStamp));
        String[] s = sd.split(" ");
        String[] timePrefix = s[0].split("-");
        String[] timeSuffix = s[1].split(":");
        return timeSuffix[2] + " " + timeSuffix[1] + " " + timeSuffix[0] + " " +
                timePrefix[2] + " " + timePrefix[1] + " ? *";
    }

}
