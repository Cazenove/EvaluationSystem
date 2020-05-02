package com.evaluation.system.controller;

import com.evaluation.system.bean.EvaluationOuter;
import com.evaluation.system.bean.SubmitOuter;
import com.evaluation.system.service.SubmitOuterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
@RestController
public class SubmitOuterController {
    @Autowired
    private SubmitOuterService submitOuterService;
    /*某组间评价表内容*/
    @PostMapping(value = "/user/evaluation/outer")
    public Map<String, Object> showEvaluation(@RequestBody SubmitOuter submitOuter){
        return submitOuterService.showEvaluationOuter(submitOuter);
    }

    /*提交组间评价表*/
    @PostMapping(value = "/user/evaluation/outer/submit")
    public Map<String, Object> submitEvaluation(@RequestBody SubmitOuter submitOuter){
        return submitOuterService.submitEvaluationOuter(submitOuter);
    }
}
