package com.evaluation.system.service;


import com.evaluation.system.bean.EvaluationOuter;
import com.evaluation.system.dao.EvaluationOuterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 221701109_肖玮昊
 */
@Service
public class GroupEvaluationService {
    @Autowired
    private EvaluationOuterRepository evaluationOuterRepository;

    //按班级号显示组间评价列表
    @Transactional(rollbackFor = Exception.class)
    public Map<String,Object> listEvaluation(EvaluationOuter evaluationOuter){
        List<EvaluationOuter> evaluationOuters = evaluationOuterRepository.findAllByClassId(evaluationOuter.getClassId());
        Map<String, Object> result = new HashMap<>();
        try {
            Map<String, Object> empty = new HashMap<>(2);
            if (!evaluationOuters.isEmpty()) {
                result.put("status", 1);
                for (int i = 0; i < evaluationOuters.size(); i++) {
                    evaluationOuters.get(i).setContent(empty);
                }
                result.put("data", evaluationOuters);
            }
        }
        catch (Exception e){
            result.put("status",0);
            result.put("msg","获取失败");
        }
        return result;
    }
}
