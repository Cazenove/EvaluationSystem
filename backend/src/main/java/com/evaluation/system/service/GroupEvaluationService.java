package com.evaluation.system.service;

import com.evaluation.system.bean.EvaluationOuter;
import com.evaluation.system.dao.GroupEvaluationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author 221701109_肖玮昊
 */
@Service
public class GroupEvaluationService {
    @Autowired
    private GroupEvaluationRepository groupEvaluation;

    //按班级号显示组间评价列表
    public  HashMap<String,Object> listEvaluation(EvaluationOuter evaluationOuter){
        int classId = evaluationOuter.getClassId();
        List<EvaluationOuter> evaluationOuters = groupEvaluation.findAllByClassId(classId);
        HashMap<String,Object> result = new HashMap<>(100);
        if (!evaluationOuters.isEmpty()){
            result.put("status",1);
            evaluationOuters.remove("content");
            result.put("data",evaluationOuters);
        }
        else {
            result.put("status",0);
            result.put("msg","获取失败");
        }
        return result;
    }
    /*获取组间评价表内容*/
    public HashMap<String, Object> showEvaluationOuter(EvaluationOuter evaluationOuter){
        HashMap<String,Object> result = new HashMap<>();
        String flag = "0";
        String msg = "获取失败，请检查评价表号";
        EvaluationOuter getEvaluation = groupEvaluation.findByEvaluationOuterId(evaluationOuter.getEvaluationOuterId());
        if (getEvaluation!=null){
            result.put("status",1);
            result.put("data",getEvaluation);
        }
        else {
            result.put("status", 0);
            result.put("msg", msg);
        }
        return result;
    }

    /*提交组间评价表*/
    @Transactional
    public HashMap<String, Object> submitEvaluationOuter(EvaluationOuter evaluationOuter){
        int flag = 0;
        String msg = "提交失败";
        HashMap<String,Object> result = new HashMap<>();
        EvaluationOuter saveEvaluation = new EvaluationOuter();
        saveEvaluation.setContent(evaluationOuter.getContent());
        saveEvaluation.setName(evaluationOuter.getName());
        saveEvaluation.setClassId(evaluationOuter.getClassId());
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String time = formatter.format(date);
        saveEvaluation.setReleaseTime(time);
        if (groupEvaluation.save(saveEvaluation).getContent()!=null) {
            flag = 1;
            msg = "提交成功";
        }
        evaluationOuter.getContent().get("suggestions");
        result.put("status",flag);
        result.put("msg",msg);
        return result;
    }
}
