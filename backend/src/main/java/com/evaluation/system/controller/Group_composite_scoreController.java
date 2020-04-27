package com.evaluation.system.controller;

import com.evaluation.system.bean.Group_composite_score;
import com.evaluation.system.dao.Group_composite_scoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value="/admin/group")
public class Group_composite_scoreController {
    @Autowired
    private Group_composite_scoreRepository group_composite_scoreRepository;

    //获取所有小组综合得分的函数
    @GetMapping(value="/composite")
    public List<Group_composite_score> listGroupCompositeScore(){
        return group_composite_scoreRepository.findAll();
    }
}
