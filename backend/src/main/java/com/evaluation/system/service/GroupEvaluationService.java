package com.evaluation.system.service;

import com.evaluation.system.bean.Evaluation_outer;
import com.evaluation.system.dao.Group_Evaluation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GroupEvaluationService {
    @Autowired
    private Group_Evaluation group_evaluation;
    /*获取组间评价表内容*/
    public Evaluation_outer showEvaluationOuter(int id){
        Evaluation_outer evaluation_outer = group_evaluation.findByEvaluationOuterId(id);
        return evaluation_outer;
    }

    /*提交组间评价表*/
    public boolean submitEvaluationOuter(int id,String name,String content,String time){
        boolean flag = false;
        Evaluation_outer evaluation_outer = new Evaluation_outer();
        evaluation_outer.setContent(content);
        evaluation_outer.setName(name);
        evaluation_outer.setClass_id(id);
        evaluation_outer.setRelease_time(time);
        if (group_evaluation.save(evaluation_outer).getContent()!=null) flag=true;
        return flag;
    }
}
