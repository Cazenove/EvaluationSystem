package com.evaluation.system.controller;

import com.evaluation.system.bean.Class;
import com.evaluation.system.bean.EvaluationOuter;
import com.evaluation.system.dao.ClassRepository;
import com.evaluation.system.service.ClassService;
import com.evaluation.system.service.EvaluationOuterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author lenovo
 */
@RestController
public class EvaluationOuterController {
    @Autowired
    private EvaluationOuterService evaluationOuterService;

    @PostMapping(value = "/admin/evaluation/outer/create")
    public Map<String, Object> creatEvaluation(@RequestBody EvaluationOuter evaluationOuter){
        return evaluationOuterService.saveEvaluationOuter(evaluationOuter);
    }

    @PostMapping(value = "/admin/evaluation/outer/find")
    public Map<String, Object> findEvaluation(@RequestBody EvaluationOuter evaluationOuter){
        return evaluationOuterService.findEvaluationOuter(evaluationOuter);
    }


}
