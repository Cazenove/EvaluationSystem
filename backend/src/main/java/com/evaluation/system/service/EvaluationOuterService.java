package com.evaluation.system.service;

import com.evaluation.system.bean.Class;
import com.evaluation.system.bean.EvaluationInner;
import com.evaluation.system.bean.EvaluationOuter;
import com.evaluation.system.bean.SubmitOuter;
import com.evaluation.system.config.EvaluationScheduleTask;
import com.evaluation.system.controller.EvaluationOuterController;
import com.evaluation.system.dao.ClassRepository;
import com.evaluation.system.dao.EvaluationInnerRepository;
import com.evaluation.system.dao.EvaluationOuterRepository;
import com.evaluation.system.dao.SubmitOuterRepository;
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


    @Transactional(rollbackFor = Exception.class)
    public Map<String,Object> saveEvaluationOuter(EvaluationOuter evaluationOuter,int flag)
    {
        Map<String,Object> result =new HashMap<>();
        result.put("status","0");
        result.put("msg","班级不存在");
        try {
            EvaluationInner evaluationInner = new EvaluationInner();
            Class c = classRepository.findByClassId(evaluationOuter.getClassId());
            if(c == null) {
                return result;
            }
            evaluationInner.setClassInfo(c);
            evaluationInner.setContent(evaluationOuter.getContent());
            evaluationInner.setName(evaluationOuter.getName());
            evaluationInner.setReleaseTime(evaluationOuter.getReleaseTime());
            evaluationInner.setEndTime(evaluationOuter.getEndTime());
            evaluationOuter.setClassInfo(c);
            EvaluationOuter e = evaluationOuterRepository.save(evaluationOuter);
            evaluationInner.setEvaluationInnerId(e.getEvaluationOuterId());
            evaluationInnerRepository.save(evaluationInner);
            if(flag==1){
                result.put("status","1");
                result.put("msg","创建成功");
            }
            else{
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
        int id = evaluationOuter.getEvaluationOuterId();
        Map<String,Object> result =new HashMap<>();
        try {
            evaluationOuterRepository.deleteById(id);
            evaluationInnerRepository.deleteById(id);
            result.put("status","1");
            result.put("msg","删除成功");
        }
        catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            result.put("status","0");
            result.put("msg","删除失败");
        }
        return result;
    }

    /**
     * 统计这次作业各个小组分数，由于json太多嵌套map写的很乱
     * @param evaluationOuter
     * @return Map
     */
    public Map<String,Object> statEvaluationOuter(EvaluationOuter evaluationOuter)
    {
        Map<String,Object> result =new HashMap<>();
        try {
            EvaluationOuter e = evaluationOuterRepository.getOne(evaluationOuter.getEvaluationOuterId());
            List<SubmitOuter> subList = submitOuterRepository.findByEvaluationOuterId(e.getEvaluationOuterId());
            int groupNum = e.getClassInfo().getGroupNum();
            if (subList.size() != groupNum) {
                result.put("status", "0");
                result.put("msg", "还有小组未提交评分表，还无法统计");
                return result;
            }
            Map<Integer, Map<String,Double>> statis = new HashMap<>();
            for (SubmitOuter submitOuter : subList) {
                Map content = submitOuter.getContent();
                List<Map> details = (List) content.get("details");
                for (Map map : details) {
                    int groupId = (int) map.get("groupId");
                    if (!statis.containsKey(groupId)) {
                        statis.put(groupId, new HashMap<String, Double>(0));
                    }
                    Double sum = this.toDouble((Integer) map.get("score"));
                    List<Map> itemContent = (List) map.get("content");
                    if (!statis.get(groupId).containsKey("score")) {
                        statis.get(groupId).put("score", sum);
                    } else {
                        Double d = (Double) statis.get(groupId).get("score");
                        statis.get(groupId).put("score", d + sum);
                    }
                    for (Map itemMap : itemContent) {
                        Map<String, Double> items;
                        if (!statis.containsKey(groupId)) {
                            items = new HashMap<>();
                        } else {
                            items = statis.get(groupId);
                        }
                        String item = (String) itemMap.get("item");
                        Double score = this.toDouble((Integer) itemMap.get("score"));
                        if (!items.containsKey(item)) {
                            items.put(item, score);
                        } else {
                            Double d = items.get(item);
                            items.put(item, d + score);
                        }
                    }
                }
            }

            for(Integer groupId:statis.keySet())
            {
                Map<String,Double> map= statis.get(groupId);
                for(String item :map.keySet())
                {
                    Double sum = map.get(item);
                    map.put(item,sum / (groupNum - 1));
                }
            }

            Map<String,Object> origin = e.getContent();
            List originDetails = (List)origin.get("details");
            for(int i = 0 ; i < originDetails.size() ;i++)
            {
                Map groupScore = (Map)originDetails.get(i);
                int id = (int)groupScore.get("groupId");
                groupScore.put("score",statis.get(id).get("score").intValue());
                List<Map> detailScore = (List)groupScore.get("content");
                for(Map map:detailScore)
                {
                    String item = (String)map.get("item");
                    map.put("score",statis.get(id).get(item).intValue());
                }
            }
            evaluationOuterRepository.save(e);

        }
        catch (Exception e)
        {
            result.put("status", "0");
            result.put("msg", "还有小组未提交评分表，还无法统计");
            return result;
        }


        return result;

    }

    public Double toDouble (Integer i)
    {
        String s = String.valueOf(i);
        return Double.valueOf(s);
    }

    public int toInt (Double d)
    {
        int a = d.intValue();
        return a;
    }

}
