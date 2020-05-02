package com.evaluation.system.controller;

import com.evaluation.system.bean.Class;
import com.evaluation.system.bean.EvaluationOuter;
import com.evaluation.system.dao.ClassRepository;
import com.evaluation.system.service.ClassService;
import com.evaluation.system.service.EvaluationOuterService;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author 221701128 chh
 */
@RestController
public class EvaluationOuterController {
    @Autowired
    private EvaluationOuterService evaluationOuterService;

    @PostMapping(value = "/admin/evaluation/create")
    public Map<String, Object> creatEvaluation(@RequestBody EvaluationOuter evaluationOuter){
        return evaluationOuterService.saveEvaluationOuter(evaluationOuter,1);
    }

    @GetMapping(value = "/admin/evaluation/details")
    public Map<String, Object> findEvaluation(){
        return evaluationOuterService.findEvaluationOuter();
    }

    @PostMapping(value = "/admin/evaluation/delete")
    public Map<String, Object> deleteEvaluation(@RequestBody EvaluationOuter evaluationOuter){

        return evaluationOuterService.deleteEvaluationOuter(evaluationOuter);
    }

    @PostMapping(value = "/admin/evaluation/update")
    public Map<String, Object> updateEvaluation(@RequestBody EvaluationOuter evaluationOuter){
        return evaluationOuterService.saveEvaluationOuter(evaluationOuter,2);
    }


}
