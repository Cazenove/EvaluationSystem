package com.evaluation.system.controller;

import com.evaluation.system.dao.ClassRepository;
import com.evaluation.system.bean.Class;
import com.evaluation.system.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

//一个例子
@RestController
public class ClassController {
    @Autowired
    private ClassService classService;

    @Autowired
    private ClassRepository classRepository;

    @PostMapping(value = "/admin/class/create")
    public Map<String, Object> creatClass(@RequestBody Class classInfo){
        Map<String, Object> result = classService.saveClass(classInfo);
        return result;
    }

    @PostMapping(value = "/admin/class/delete")
    public Map<String, Object> deleteClass(@RequestBody Class classInfo){
        Map<String, Object> result = classService.deleteClass(classInfo);
        return result;
    }

    @PostMapping(value = "/admin/class/update")
    public Map<String, Object> updateClass(@RequestBody Class classInfo){
        Map<String, Object> result = classService.saveClass(classInfo);
        return result;
    }

    @PostMapping(value = "/admin/class")
    public Map<String, Object> findAllClass(){
        Map<String, Object> result = classService.findAllClass();
        return result;
    }

    @PostMapping(value = "/admin/class/end")
    public Map<String, Object> endClass(@RequestBody Class classInfo){
        Map<String, Object> result = classService.endClass(classInfo);
        return result;
    }

}
