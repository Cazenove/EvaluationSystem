package com.evaluation.system.controller;

import com.evaluation.system.bean.Person_composite_score;
import com.evaluation.system.dao.Person_composite_scoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value="/admin/person")
public class Person_composite_scoreController {
    @Autowired
    private Person_composite_scoreRepository person_composite_scoreRepository;

    //获取所有人的综合得分
    @GetMapping(value="/composite")
    public List<Person_composite_score> listPersonCompositeScore(){
        return person_composite_scoreRepository.findAll();
    }
}
