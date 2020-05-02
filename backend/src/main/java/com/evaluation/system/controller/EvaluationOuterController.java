package com.evaluation.system.controller;

import com.evaluation.system.bean.EvaluationOuter;
import com.evaluation.system.service.EvaluationOuterService;
import com.evaluation.system.service.GroupEvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
public class EvaluationOuterController {
    @Autowired
    private GroupEvaluationService groupEvaluationService;
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
    /*组间评价列表*/
    @PostMapping(value = "/user/evaluation/outer/list")
   public Map<String, Object> listEvaluation(@RequestBody EvaluationOuter evaluationOuter){
        return groupEvaluationService.listEvaluation(evaluationOuter);
    }

}

