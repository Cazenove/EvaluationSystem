package com.evaluation.system.controller;

import com.evaluation.system.bean.PersonScore;
import com.evaluation.system.dao.PersonScoreRepository;
import com.evaluation.system.service.PersonScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author 221701230张增燊
 */
@RestController
public class PersonScoreController {
    @Autowired
    private PersonScoreRepository personScoreReposity;
    @Autowired
    private PersonScoreService personScoreService;

    /**获取所有人的历次贡献**/
    @GetMapping(value="admin/user/score/list")
    public Map<String,Object> listPersonScore(){
        return personScoreService.listPersonScore();
    }

    /**获取某个人的历次贡献**/
    @GetMapping(value="admin/user/score/single")
    public Map<String,Object> getPersonScore(@RequestParam String userId){
        return personScoreService.getPersonScore(userId);
    }
}
