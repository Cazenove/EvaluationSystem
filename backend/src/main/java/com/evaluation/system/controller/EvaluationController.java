package com.evaluation.system.controller;

import com.evaluation.system.bean.Evaluation_outer;
import com.evaluation.system.dao.GroupEvaluation;
import com.evaluation.system.service.GroupEvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController

public class EvaluationController {
    @Autowired
    private GroupEvaluation group_evaluation;
    @Autowired
    private GroupEvaluationService groupEvaluationService;
    /*组间评价列表*/
    @PostMapping(value = "/listEvaluation")
    public List<Evaluation_outer> listEvaluation(){
        return group_evaluation.findAll();
    }

    /*某组间评价表内容*/
    @PostMapping(value = "/showEvaluation")
    public Evaluation_outer showEvaluation(@RequestParam("evaluation_outer_id") int id){
        return groupEvaluationService.showEvaluationOuter(id);
    }

    /*提交组间评价表*/
    @PostMapping(value = "/submitEvaluation")
    public boolean submitEvaluation(@RequestParam("class_id") int id, @RequestParam("name") String name,
                                    @RequestParam("content") String content){
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String time = formatter.format(date);
        return groupEvaluationService.submitEvaluationOuter(id, name, content,time);
    }
}

