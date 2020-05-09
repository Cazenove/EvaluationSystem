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

        Map<String, Object> dataMap = new HashMap<>();
        result.put("status", 1);
        dataMap.put("groupId",groupId);
        dataMap.put("groupName",null);
        dataMap.put("classId",null);
        dataMap.put("className",null);
        dataMap.put("groupNum",null);
        dataMap.put("leader",null);
        dataMap.put("member",null);
        dataMap.put("data",null);
        result.put("data",dataMap);

        //有小组为1，无小组为0
        int flag = 0;

        try{
            //获取groupName，classId，groupNum
            Team group = teamRepository.findOneByGroupId(groupId);
            String groupName = group.getGroupName();
            int classId = group.getClassId();
            int groupNum = group.getGroupNum();
            dataMap.put("groupId",groupId);
            dataMap.put("groupName",groupName);
            dataMap.put("classId",classId);
            dataMap.put("groupNum",groupNum);

            flag = 1;

            //获取className by classId
            String className = classRepository.findByClassId(classId).getClassName();
            dataMap.put("className",className);

            //获取leader的userId，userName
            User leader = userRepository.findOneByGroupIdAndAndStatus(groupId,"2");
            String leaderUserId = leader.getUserId();
            String leaderName = leader.getName();
            dataMap.put("leader",leader);

            //获取member的userId，userName
            ArrayList<User> memberList = userRepository.findByGroupIdAndStatus(groupId,"1");
            dataMap.put("member",memberList);

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
            dataMap.put("data",evaluationInfoList);
        } catch (Exception e) {
            if(flag == 0){
                //小组不存在
                result.put("status", 0);
                result.put("msg", "小组不存在");
            }
            return result;
        } finally {
            //小组存在但是信息不齐全
            return result;
        }

    }

    @Autowired
    private PersonScoreRepository personScoreRepository;

    /**
     * 获取组员详情
     * @author 221701310_陈家祯
     */
    public Map<String, Object> getGroupMemberInfo(String userId) {
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("userId", userId);
        dataMap.put("userName", null);
        result.put("status",null);
        result.put("data",dataMap);

        try {
            //获取组员姓名
            String name = userRepository.findOneByUserId(userId).getName();
            dataMap.put("userId", userId);
            dataMap.put("userName", name);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            result.put("status", 0);
            result.put("msg", "查询发生错误");
            return result;
        }

        try{
            //存放组员贡献率分工情况
            PersonScore personScore = personScoreRepository.findByUserId(userId);
            dataMap.put("content",personScore.getContent());
            result.put("status",1);
            result.put("data",dataMap);
        } catch(Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            dataMap.put("content",null);
            result.put("status",1);
            result.put("data",dataMap);
            return result;
        }
        return result;
    }

}
