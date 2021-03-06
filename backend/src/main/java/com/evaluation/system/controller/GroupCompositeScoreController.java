package com.evaluation.system.controller;

import com.evaluation.system.dao.GroupCompositeScoreRepository;
import com.evaluation.system.service.GroupCompositeScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author 221701230张增燊
 */
@RestController
public class GroupCompositeScoreController {
    @Autowired
    private GroupCompositeScoreService groupCompositeScoreService;


    /**获取所有小组综合得分**/
    @GetMapping(value="admin/group/score/final")
    public Map<String,Object> listGroupCompositeScore(){
        return groupCompositeScoreService.listGroupScore();
    }

    /**获取小组的综合得分,待测试**/
    @GetMapping(value="admin/group/composite/score")
    public Map<String,Object> getGroupCompositeScore(@RequestParam String groupId){
        return groupCompositeScoreService.getGroupScore(groupId);
    }
}
