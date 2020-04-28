package com.evaluation.system.controller;

import com.evaluation.system.ClassRepository;
import com.evaluation.system.bean.Class;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//一个例子
@RestController
public class ClassController {
    @Autowired
    private ClassRepository classRepository;

    //列出所有管理员
    @RequestMapping(value = "/admin/class/create")
    public List<Class> listAdmin(){
        return classRepository.findAll();
    }
}
