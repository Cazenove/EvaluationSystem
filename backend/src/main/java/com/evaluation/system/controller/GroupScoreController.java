package com.evaluation.system.controller;

import com.evaluation.system.bean.GroupScore;
import com.evaluation.system.dao.GroupScoreRepository;
import com.evaluation.system.service.GroupScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author 221701230张增燊
 */
@RestController
public class GroupScoreController {
    @Autowired
    private GroupScoreService groupScoreService;

    /**返回所有小组历次得分**/
    @GetMapping(value="admin/group/score/list")
    public Map<String, Object> listGroupScore(){
        return groupScoreService.listGroupScore();
    }

    @GetMapping(value="admin/group/score/single")
    public Map<String,Object> getGroupScore(@RequestParam String groupId){
        return groupScoreService.getGroupScore(groupId);
    }
}
