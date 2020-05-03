package com.evaluation.system.service;

import com.evaluation.system.bean.GroupSuggestion;
import com.evaluation.system.dao.GroupSuggestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 221701230张增燊
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class GroupSuggestionService {

    @Autowired
    private GroupSuggestionRepository groupSuggestionRepository;

    public Map<String,Object> createSuggestion(Map<String,Object> content){
        Map<String,Object> map=new HashMap<String,Object>();
        GroupSuggestion groupSuggestion =new GroupSuggestion();
        groupSuggestion.setEvaluationOuterId((int)content.get("evaluationId"));
        groupSuggestion.setGroupId((int)content.get("targetGroupId"));
        groupSuggestion.setSuggestion((String) content.get("suggestion"));
        try{
            groupSuggestionRepository.save(groupSuggestion);
            map.put("status",1);
            map.put("msg","新增成功");
        }catch(Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            map.put("status",0);
            map.put("msg","新增失败");
        }
        return map;
    }

    public Map<String,Object> deleteSuggestion(Map<String,Object> content){
        Map<String,Object> map = new HashMap<String,Object>(16);
        try{
            groupSuggestionRepository.deleteById((int)content.get("groupSuggestionId"));
            map.put("status",1);
            map.put("msg","删除成功");
        }catch(Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            map.put("status",0);
            map.put("msg","删除失败");
        }
        return map;
    }

    public Map<String,Object> updateSuggestion(Map<String,Object> content){
        Map<String,Object> map = new HashMap<String,Object>(16);
        try{
            GroupSuggestion groupSuggestion =groupSuggestionRepository.findByGroupSuggestionId((int)content.get("groupSuggestionId"));
            groupSuggestion.setSuggestion((String) content.get("suggestion"));
            groupSuggestionRepository.save(groupSuggestion);

            map.put("status",1);
            map.put("msg","修改成功");
        }catch(Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            map.put("status",0);
            map.put("msg","修改失败");
        }
        return map;
    }

    public Map<String,Object> listSuggestion(){
        Map<String,Object> map = new HashMap<String,Object>(16);
        try{
            map.put("status",1);
            map.put("data",groupSuggestionRepository.findAll());

        }catch(Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            map.put("status",0);
            map.put("msg","查询失败");
        }
        return map;
    }
}
