package com.evaluation.system.service;

import com.evaluation.system.bean.*;
import com.evaluation.system.bean.Class;
import com.evaluation.system.dao.ClassRepository;
import com.evaluation.system.dao.PersonScoreRepository;
import com.evaluation.system.dao.TeamRepository;
import com.evaluation.system.dao.UserRepository;
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

            //for(int i=0;i<personScores.size();i++)
            //{
//                User user=userRepository.findByUserId(personScores.get(i).getUserId());
//                personScores.get(i).setUserName(user.getName());
//                Class c = classRepository.findByClassId(user.getClassId());
//                personScores.get(i).setClassId(user.getClassId());
//                personScores.get(i).setClassName(c.getClassName());
//                Team team=teamRepository.findByGroupId(personScores.get(i).getGroupId());
//                personScores.get(i).setGroupName(team.getGroupName());
            //}
            //map.put("data",personScores);

//            List<User> users=userRepository.findAll();
//            List<PersonScoreHelper> personScoreHelpers=new ArrayList<>();
//
//            for(int i=0;i<users.size();i++){
//                PersonScoreHelper personScoreHelper=new PersonScoreHelper();
//
//
//                User user=users.get(i);
//
//                List<PersonScore> personScores=personScoreReposity.findByUserId(user.getUserId());
//                if(personScores!=null)
//                {
//                    Class c = classRepository.findByClassId(user.getClassId());
//                    Team team=teamRepository.findByGroupId(user.getGroupId());
//
//                    personScoreHelper.setClassId(user.getClassId());
//                    personScoreHelper.setClassName(c.getClassName());
//                    personScoreHelper.setGroupName(team.getGroupName());
//                    personScoreHelper.setUserName(user.getName());
//                    personScoreHelper.setPersonScores(personScores);
//                    personScoreHelper.setTelephone(user.getTelephone());
//                    personScoreHelper.setUserId(user.getUserId());
//
//                    personScoreHelpers.add(personScoreHelper);
//                }
//
//            }

            map.put("data",personScores);
            map.put("status","1");

        }catch(Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            map.put("status","0");
            map.put("msg","查询发生错误");
        }
        return map;
    }

    public Map<String,Object> getPersonScore(String userId){
        Map<String,Object> map = new HashMap<String,Object>();
        try{
            //Map<String,Object> data=new HashMap<>();
            List<PersonScore> personScores=personScoreReposity.findByUserId(userId);

            PersonScoreHelper personScoreHelper=new PersonScoreHelper();
//            for(int i=0;i<personScores.size();i++)
//            {
//                User user=userRepository.findByUserId(personScores.get(0).getUserId());
//                personScores.get(0).setUserName(user.getName());
//                Class c = classRepository.findByClassId(user.getClassId());
//                personScores.get(0).setClassId(user.getClassId());
//                personScores.get(0).setClassName(c.getClassName());
//                Team team=teamRepository.findByGroupId(personScores.get(0).getGroupId());
//                personScores.get(0).setGroupName(team.getGroupName());
//            }
            User user=userRepository.findByUserId(personScores.get(0).getUserId());
            //personScores.get(0).setUserName(user.getName());
            Class c = classRepository.findByClassId(user.getClassId());
            //personScores.get(0).setClassId(user.getClassId());
            //personScores.get(0).setClassName(c.getClassName());
            Team team=teamRepository.findByGroupId(personScores.get(0).getGroupId());
            //personScores.get(0).setGroupName(team.getGroupName());

//            data.put("userName",user.getName());
//            data.put("classId",user.getClassId());
//            data.put("className",c.getClassName());
//            data.put("groupName",team.getGroupName());
//            data.put("detail",personScores);

            personScoreHelper.setClassId(user.getClassId());
            personScoreHelper.setClassName(c.getClassName());
            personScoreHelper.setGroupName(team.getGroupName());
            personScoreHelper.setUserName(user.getName());
            personScoreHelper.setPersonScores(personScores);
            personScoreHelper.setTelephone(user.getTelephone());
            personScoreHelper.setUserId(user.getUserId());

            map.put("data",personScoreHelper);
            map.put("status","1");
        }catch(Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            map.put("status","0");
            map.put("msg","查询发生错误");
        }
        return map;
    }
}
