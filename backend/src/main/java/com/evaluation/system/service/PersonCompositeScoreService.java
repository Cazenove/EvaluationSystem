package com.evaluation.system.service;

import com.evaluation.system.bean.*;
import com.evaluation.system.bean.Class;
import com.evaluation.system.dao.*;
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
public class PersonCompositeScoreService {
    @Autowired
    private PersonCompositeScoreRepository personCompositeScoreRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private ClassRepository classRepository;

    public Map<String,Object> listPersonCompositeScore(){
        Map<String,Object> map = new HashMap<String,Object>();
        try{
            /**获取所有小组的分数**/
            List<PersonCompositeScore> personCompositeScores=personCompositeScoreRepository.findAll();

            for(int i=0;i<personCompositeScores.size();i++)
            {
                Map<String,Object> data=new HashMap<>();
                User user=userRepository.findByUserId(personCompositeScores.get(i).getUserId());
                personCompositeScores.get(i).setUserName(user.getName());
                personCompositeScores.get(i).setClassId(user.getClassId());
                Class c = classRepository.findByClassId(user.getClassId());
                personCompositeScores.get(i).setClassName(c.getClassName());
                Team team=teamRepository.findByGroupId(personCompositeScores.get(i).getGroupId());
                personCompositeScores.get(i).setGroupName(team.getGroupName());
            }
            map.put("data",personCompositeScores);
            map.put("status","1");

        }catch(Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            map.put("status","0");
            map.put("msg","查询发生错误");
        }
        return map;
    }
}
