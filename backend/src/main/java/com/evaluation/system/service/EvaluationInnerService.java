package com.evaluation.system.service;

import com.evaluation.system.bean.EvaluationInner;
import com.evaluation.system.bean.Team;
import com.evaluation.system.dao.EvaluationInnerRepository;
import com.evaluation.system.dao.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EvaluationInnerService {

    @Autowired
    private EvaluationInnerRepository evaluationInnerRepository;

    @Autowired
    private TeamRepository teamRepository;

    /*根据班级id找组内评分表*/
    public Map<String, Object> getEvaluationInnersByClassId(int classId) {
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

    /*获取一份组内评分表内容*/
    public Map<String, Object> getOneEvaluationInner(int id, int classId, int groupId) {
        Map<String, Object> result = new HashMap<>();
        try {
            EvaluationInner evaluationInner = evaluationInnerRepository.findOneByEvaluationInnerId(id);
            Team team = teamRepository.findByGroupId(groupId);

            Map<String, Object> dataMap = new HashMap<>();
            if(evaluationInner != null) {
                result.put("status",1);
                dataMap.put("evaluationInnerId",id);
                dataMap.put("name",evaluationInner.getName());
                dataMap.put("groupId",groupId);
                dataMap.put("groupName",team.getGroupName());
                dataMap.put("releaseTime",evaluationInner.getReleaseTime());
                dataMap.put("endTime",evaluationInner.getEndTime());
                dataMap.put("content",evaluationInner.getContent());
                result.put("data",dataMap);
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
