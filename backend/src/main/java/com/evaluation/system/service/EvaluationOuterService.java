package com.evaluation.system.service;

import com.evaluation.system.bean.Class;
import com.evaluation.system.bean.EvaluationOuter;
import com.evaluation.system.dao.ClassRepository;
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
    private ClassRepository classRepository;

    @Transactional
    public Map<String,Object> saveEvaluationOuter(EvaluationOuter evaluationOuter)
    {
        Map<String,Object> result =new HashMap<>();
        result.put("status","false");
        try {
            Class c = classRepository.findByClassId(evaluationOuter.getClassId());
            if(c == null) {
                return result;
            }
            evaluationOuter.setClassInfo(c);
            evaluationOuterRepository.save(evaluationOuter);
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
            result.put("date",e.get());
            result.put("status","true");
        }
        catch (Exception e)
        {
            result.put("status","false");
        }

        return result;
    }



}
