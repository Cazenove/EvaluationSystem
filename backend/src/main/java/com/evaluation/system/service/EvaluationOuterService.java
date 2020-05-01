package com.evaluation.system.service;

import com.evaluation.system.bean.Class;
import com.evaluation.system.bean.EvaluationInner;
import com.evaluation.system.bean.EvaluationOuter;
import com.evaluation.system.dao.ClassRepository;
import com.evaluation.system.dao.EvaluationInnerRepository;
import com.evaluation.system.dao.EvaluationOuterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class EvaluationOuterService {

    @Autowired
    private EvaluationOuterRepository evaluationOuterRepository;

    @Autowired
    private EvaluationInnerRepository evaluationInnerRepository;

    @Autowired
    private ClassRepository classRepository;

    @Transactional
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
            if(flag==1){
                result.put("status","0");
                result.put("msg","创建失败，请检查是否重名或填写是否规范");
            }
            else{
                result.put("status","0");
                result.put("msg","修改失败，请检查是否重名或填写是否规范");
            }
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

    @Transactional
    public Map<String,Object> deleteEvaluationOuter(EvaluationOuter evaluationOuter)
    {
        int id = evaluationOuter.getEvaluationOuterId();
        Map<String,Object> result =new HashMap<>();
        evaluationOuterRepository.deleteById(id);
        evaluationInnerRepository.deleteById(id);
        result.put("status","1");
        result.put("msg","删除成功");
        return result;
    }

}
