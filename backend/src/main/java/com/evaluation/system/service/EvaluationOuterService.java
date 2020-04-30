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

    public Map<String,Object> saveEvaluationOuter(EvaluationOuter evaluationOuter)
    {
        Map<String,Object> result =new HashMap<>();
        result.put("status","false");
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
            evaluationOuterRepository.save(evaluationOuter);
            evaluationInnerRepository.save(evaluationInner);
            result.put("status","true");
        }
        catch (Exception e)
        {
            result.put("status","false");
        }

        return result;
    }

    public Map<String,Object> findEvaluationOuter(EvaluationOuter evaluationOuter)
    {
        Map<String,Object> result =new HashMap<>();
        try {
            Optional<EvaluationOuter> e = evaluationOuterRepository.findById(evaluationOuter.getEvaluationOuterId());
            System.out.println(evaluationOuter.getClassInfo());
            e.ifPresent(outer -> result.put("date", outer));
            result.put("status","true");
        }
        catch (Exception e)
        {
            result.put("status","false");
        }

        return result;
    }



}
