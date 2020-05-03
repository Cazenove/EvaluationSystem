package com.evaluation.system.service;

import com.evaluation.system.bean.Class;
import com.evaluation.system.bean.Team;
import com.evaluation.system.bean.User;
import com.evaluation.system.dao.ClassRepository;
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
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ClassRepository classRepository;

    public Map<String,Object> listTeams(){
        Map<String,Object> map = new HashMap<String,Object>(16);
        try{
            List<Team> teams=teamRepository.findAll();
            for(int i=0;i<teams.size();i++)
            {
                Team team=teams.get(i);

                Class c= classRepository.findByClassId(team.getClassId());
                team.setClassName(c.getName());

                List<User> members = userRepository.findByGroupId(team.getGroupId());
                team.setMembers(members);

                for(int j=0;j<members.size();j++)
                {
                    if(members.get(j).getStatus().contentEquals("2"))
                    {
                        team.setLeader(members.get(j));
                    }
                }
            }
            map.put("data",teams);
            map.put("status",1);
        }catch(Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            map.put("status","0");
            map.put("msg","查询失败");
        }
        return map;
    }

    public Map<String,Object> updateTeamName(Map<String,Object> content){
        Team team=teamRepository.findByGroupId((int)content.get("groupId"));
        team.setGroupName((String)content.get("groupName"));
        Map<String,Object> map=new HashMap<>();
        try{
            teamRepository.save(team);
            map.put("status",1);
            map.put("msg","修改成功");
        }catch(Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            map.put("status","0");
            map.put("msg","修改失败");
        }
        return map;
    }
}
