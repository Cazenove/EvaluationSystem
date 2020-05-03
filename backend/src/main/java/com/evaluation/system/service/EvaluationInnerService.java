package com.evaluation.system.service;

import com.evaluation.system.bean.EvaluationInner;
import com.evaluation.system.dao.EvaluationInnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EvaluationInnerService {

    @Autowired
    private EvaluationInnerRepository evaluationInnerRepository;

    /*根据班级id找组内评分表*/
    public Map<String, Object> getEvaluationInnerByClassId(int classId) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<EvaluationInner> evaluationInners = evaluationInnerRepository.findByClassId(classId);
            if(!evaluationInners.isEmpty()) {
                result.put("status",1);
                result.put("data",evaluationInners);
            } else {
                result.put("status",0);
                result.put("msg","未找到对应评分表");
            }
        } catch (Exception e) {
            result.put("status",0);
            result.put("msg",e);
        }
         return result;
    }
}
