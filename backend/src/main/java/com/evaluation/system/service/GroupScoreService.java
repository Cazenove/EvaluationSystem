package com.evaluation.system.service;

import com.evaluation.system.bean.EvaluationOuter;
import com.evaluation.system.bean.GroupScore;
import com.evaluation.system.bean.Team;
import com.evaluation.system.dao.EvaluationOuterRepository;
import com.evaluation.system.dao.GroupScoreRepository;
import com.evaluation.system.dao.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.*;

/**
 * @author 221701230张增燊
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class GroupScoreService {
    @Autowired
    private GroupScoreRepository groupScoreRepository;
    @Autowired
    private TeamRepository teamRepository;

    public Map<String,Object> listGroupScore(){
        Map<String,Object> map = new HashMap<String,Object>();
        try{
            /**获取所有小组的分数**/
            List<GroupScore> groupScores=groupScoreRepository.findAll();

            for(int i=0;i<groupScores.size();i++)
            {
                Map<String,Object> data=new HashMap<>();
                GroupScore groupScore=groupScores.get(i);
                Team team=teamRepository.findByGroupId(groupScore.getGroupId());

                groupScore.setClassId(team.getClassId());
                groupScore.setGroupNum(team.getGroupNum());
                groupScore.setGroupName(team.getGroupName());
            }
            map.put("data",groupScores);
            map.put("status","1");

        }catch(Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            map.put("status","0");
            map.put("msg","查询发生错误");
        }
        return map;
    }

    public Map<String,Object> getGroupScore(String groupId){
        Map<String,Object> map = new HashMap<String,Object>();
        try{
            GroupScore groupScore=groupScoreRepository.findByGroupId(Integer.valueOf(groupId));
            Team team=teamRepository.findByGroupId(groupScore.getGroupId());

            groupScore.setClassId(team.getClassId());
            groupScore.setGroupNum(team.getGroupNum());
            groupScore.setGroupName(team.getGroupName());
            map.put("data",groupScore);
            map.put("status","1");

        }catch(Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            map.put("status","0");
            map.put("msg","查询发生错误");
        }
        return map;
    }
}
