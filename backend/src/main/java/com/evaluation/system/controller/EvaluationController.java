package com.evaluation.system.controller;

import com.evaluation.system.bean.EvaluationOuter;
import com.evaluation.system.service.GroupEvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;

@RestController
public class EvaluationController {
    @Autowired
    private GroupEvaluationService groupEvaluationService;
    /*组间评价列表*/
    @PostMapping(value = "/user/evaluation/outer/list")
    public HashMap<String, Object> listEvaluation( @RequestBody EvaluationOuter evaluationOuter){
        return groupEvaluationService.listEvaluation(evaluationOuter);
    }

    /*某组间评价表内容*/
    @PostMapping(value = "/user/evaluation/outer")
    public HashMap<String, Object> showEvaluation(@RequestBody EvaluationOuter evaluationOuter){
        return groupEvaluationService.showEvaluationOuter(evaluationOuter);
    }

    /*提交组间评价表*/
    @PostMapping(value = "/user/evaluation/outer/submit")
    public HashMap<String, Object> submitEvaluation(@RequestBody EvaluationOuter evaluationOuter){
        return groupEvaluationService.submitEvaluationOuter(evaluationOuter);
    }
}

