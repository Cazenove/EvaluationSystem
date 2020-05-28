package com.evaluation.system.service;

import com.evaluation.system.bean.GroupCompositeScore;
import com.evaluation.system.bean.GroupScore;
import com.evaluation.system.bean.Team;
import com.evaluation.system.dao.GroupCompositeScoreRepository;
import com.evaluation.system.dao.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 221701230张增燊
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class GroupCompositeScoreService {
    @Autowired
    private GroupCompositeScoreRepository groupCompositeScoreRepository;
    @Autowired
    private TeamRepository teamRepository;


    public Map<String,Object> listGroupScore(){
        Map<String,Object> map = new HashMap<String,Object>();
        try{
            /**获取所有小组的分数**/
            List<GroupCompositeScore> groupCompositeScores=groupCompositeScoreRepository.findAll();
            for(int i=0;i<groupCompositeScores.size();i++)
            {
                int temp=groupCompositeScores.get(i).getGroupId();
                Team team=teamRepository.findByGroupId(temp);

                groupCompositeScores.get(i).setClassId(team.getClassId());
                groupCompositeScores.get(i).setGroupName(team.getGroupName());
                groupCompositeScores.get(i).setGroupNum(team.getGroupNum());
            }
            map.put("data",groupCompositeScores);
            map.put("status","1");

        }catch(Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            map.put("status","0");
            map.put("msg","查询发生错误");
        }
        return map;
    }

    public Map<String,Object> getGroupScore(Map<String,Object> content){
        Map<String,Object> map=new HashMap<>();
        try{
            String temp=(String)content.get("groupId");

            GroupCompositeScore groupCompositeScore=groupCompositeScoreRepository.findByGroupId(Integer.valueOf(temp));

            Team team=teamRepository.findByGroupId(Integer.valueOf(temp));
            groupCompositeScore.setClassId(team.getClassId());
            groupCompositeScore.setGroupName(team.getGroupName());
            groupCompositeScore.setGroupNum(team.getGroupNum());

            map.put("data",groupCompositeScore);
            map.put("status","1");

        }catch(Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            map.put("status","0");
            map.put("msg","查询发生错误");
        }
        return map;
    }


}
