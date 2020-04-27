package com.evaluation.system.controller;

import com.evaluation.system.bean.Group_score;
import com.evaluation.system.dao.Group_scoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value="/admin/group")
public class Group_scoreContoller {
    @Autowired
    private Group_scoreRepository group_scoreRepository;

    //返回所有小组历次得分
    @GetMapping(value="/score")
    public List<Group_score> listGoupScore(){
        return group_scoreRepository.findAll();
    }
}
