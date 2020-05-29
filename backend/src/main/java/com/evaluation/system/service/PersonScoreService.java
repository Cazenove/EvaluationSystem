package com.evaluation.system.service;

import com.evaluation.system.bean.Class;
import com.evaluation.system.bean.PersonScore;
import com.evaluation.system.bean.Team;
import com.evaluation.system.bean.User;
import com.evaluation.system.dao.ClassRepository;
import com.evaluation.system.dao.PersonScoreRepository;
import com.evaluation.system.dao.TeamRepository;
import com.evaluation.system.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 221701230张增燊
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PersonScoreService {
    @Autowired
    private PersonScoreRepository personScoreReposity;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private ClassRepository classRepository;

    public Map<String,Object> listPersonScore(){
        Map<String,Object> map = new HashMap<String,Object>();
        try{
            /**获取所有人的分数**/
            List<PersonScore> personScores=personScoreReposity.findAll();

            for(int i=0;i<personScores.size();i++)
            {
                User user=userRepository.findByUserId(personScores.get(i).getUserId());
                personScores.get(i).setUserName(user.getName());
                Class c = classRepository.findByClassId(user.getClassId());
                personScores.get(i).setClassId(user.getClassId());
                personScores.get(i).setClassName(c.getClassName());
                Team team=teamRepository.findByGroupId(personScores.get(i).getGroupId());
                personScores.get(i).setGroupName(team.getGroupName());
            }
            map.put("data",personScores);
            map.put("status","1");

        }catch(Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            map.put("status","0");
            map.put("msg","查询发生错误");
        }
        return map;
    }

    public Map<String,Object> getPersonScore(Map<String,Object> content){
        Map<String,Object> map = new HashMap<String,Object>();
        try{
            String temp=(String)content.get("userId");
            PersonScore personScore=personScoreReposity.findByUserId(temp);
            User user=userRepository.findByUserId(personScore.getUserId());
            personScore.setUserName(user.getName());
            Class c = classRepository.findByClassId(user.getClassId());
            personScore.setClassId(user.getClassId());
            personScore.setClassName(c.getClassName());
            Team team=teamRepository.findByGroupId(personScore.getGroupId());
            personScore.setGroupName(team.getGroupName());
            map.put("data",personScore);
            map.put("status","1");
        }catch(Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            map.put("status","0");
            map.put("msg","查询发生错误");
        }
        return map;
    }
}
