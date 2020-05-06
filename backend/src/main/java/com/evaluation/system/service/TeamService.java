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
                team.setClassName(c.getClassName());

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
        String tId=(String)content.get("groupId");
        Team team=teamRepository.findByGroupId(Integer.valueOf(tId));
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

    @Autowired
    private GroupSuggestionRepository groupSuggestionRepository;

    @Autowired
    private EvaluationOuterRepository evaluationOuterRepository;

    @Autowired
    private GroupScoreRepository groupScoreRepository;

    /**
     * 获取小组详情
     * @author 221701310_陈家祯
     */
    public Map<String, Object> getGroupInfo(Integer groupId) {
        Map<String, Object> result = new HashMap<>();
        try{
            //获取groupName，classId，groupNum
            Team group = teamRepository.findOneByGroupId(groupId);
            String groupName = group.getGroupName();
            int classId = group.getClassId();
            int groupNum = group.getGroupNum();

            //获取className by classId
            String className = classRepository.findByClassId(classId).getClassName();

            //获取leader的userId，userName
            User leader = userRepository.findOneByGroupIdAndAndStatus(groupId,"2");
            String leaderUserId = leader.getUserId();
            String leaderName = leader.getName();

            //获取member的userId，userName
            ArrayList<User> memberList = userRepository.findByGroupIdAndStatus(groupId,"1");

            //evaluationInfo组成的List
            ArrayList<Object> evaluationInfoList = new ArrayList<>();

            //获取evaluationOuterId，name，score，suggestion
            ArrayList<GroupSuggestion> groupSuggestions = groupSuggestionRepository.findByGroupId(groupId);
            for(GroupSuggestion groupSuggestion : groupSuggestions) {

                //evaluationInfo内含evaluationOuterId，name，score，suggestion
                Map<String, Object> evaluationInfo = new HashMap<>();

                //evaluationOuterId
                int evaluationOuterId = groupSuggestion.getEvaluationOuterId();
                evaluationInfo.put("evaluationOuterId",evaluationOuterId);

                //name
                String evaluationOuterName = evaluationOuterRepository.findOneByEvaluationOuterId(evaluationOuterId).getName();
                evaluationInfo.put("name",evaluationOuterName);

                //score
                GroupScore groupScore = groupScoreRepository.findOneByEvaluationOuterId(evaluationOuterId);
                evaluationInfo.put("score",groupScore.getContent());

                //suggestion
                evaluationInfo.put("suggestion",groupSuggestion.getSuggestion());

                //将evaluationInfo加入evaluationInfoList
                evaluationInfoList.add(evaluationInfo);

            }

            Map<String, Object> dataMap = new HashMap<>();
            result.put("status", 1);
            dataMap.put("groupId",groupId);
            dataMap.put("groupName",groupName);
            dataMap.put("classId",classId);
            dataMap.put("className",className);
            dataMap.put("groupNum",groupNum);
            dataMap.put("leader",leader);
            dataMap.put("member",memberList);
            dataMap.put("data",evaluationInfoList);
            result.put("data",dataMap);
        } catch (Exception e) {
            result.put("status", 0);
            result.put("msg", e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

        return result;
    }

    @Autowired
    private PersonScoreRepository personScoreRepository;

    /**
     * 获取组员详情
     * @author 221701310_陈家祯
     */
    public Map<String, Object> getGroupMemberInfo(String userId) {
        Map<String, Object> result = new HashMap<>();
        try {
            Map<String, Object> dataMap = new HashMap<>();
            ArrayList<Object> evaluationInfoList = new ArrayList<>();

            //获取组员姓名
            String name = userRepository.findOneByUserId(userId).getName();
            dataMap.put("userId",userId);
            dataMap.put("userName",name);
            //存放组员贡献率分工情况
            PersonScore personScore = personScoreRepository.findByUserId(userId);

            dataMap.put("content",personScore.getContent());

            result.put("status",1);
            result.put("data",dataMap);
        } catch (Exception e) {
            result.put("status", 0);
            result.put("msg", e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result;
    }

}
