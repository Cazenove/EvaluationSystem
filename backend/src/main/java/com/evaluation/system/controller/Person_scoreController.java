package com.evaluation.system.controller;

import com.evaluation.system.bean.Person_score;
import com.evaluation.system.dao.Person_scoreReposity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value="/admin/person")
public class Person_scoreController {
    @Autowired
    private Person_scoreReposity person_scoreReposity;

    //获取所有人的历次贡献
    @GetMapping(value="/score")
    public List<Person_score> listPersonScore(){
        return person_scoreReposity.findAll();
    }
}
