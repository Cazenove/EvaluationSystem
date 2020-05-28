package com.evaluation.system.controller;

import com.evaluation.system.bean.PersonCompositeScore;
import com.evaluation.system.dao.PersonCompositeScoreRepository;
import com.evaluation.system.service.PersonCompositeScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author 221701230张增燊
 */
@RestController
public class PersonCompositeScoreController {
    @Autowired
    private PersonCompositeScoreService personCompositeScoreService;

    /**获取所有人的综合得分**/
    @GetMapping(value="admin/user/score/final")
    public Map<String, Object> listPersonCompositeScore(){
        return personCompositeScoreService.listPersonCompositeScore();
    }

    /**获取个人的综合得分,尚未测试**/
    @GetMapping(value="admin/user/composite/score")
    public Map<String, Object> getPersonCompositeScore(@RequestBody Map<String,Object> content){
        return personCompositeScoreService.getPersonCompositeScore(content);
    }
}
