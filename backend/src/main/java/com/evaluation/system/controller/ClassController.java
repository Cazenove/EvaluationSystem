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

/**
 * @author 221701128 chh
 */
@RestController
public class ClassController {
    @Autowired
    private ClassService classService;


    @PostMapping(value = "/admin/class/create")
    public Map<String, Object> creatClass(@RequestBody Class classInfo){
        return classService.saveClass(classInfo,1);
    }

    @PostMapping(value = "/admin/class/delete")
    public Map<String, Object> deleteClass(@RequestBody Class classInfo){
        return classService.deleteClass(classInfo);
    }

    @PostMapping(value = "/admin/class/update")
    public Map<String, Object> updateClass(@RequestBody Class classInfo){
        return classService.saveClass(classInfo,2);
    }

    @GetMapping(value = "/admin/class/list")
    public Map<String, Object> findAllClass(){
        return classService.findAllClass();
    }

    @PostMapping(value = "/admin/class/end")
    public Map<String, Object> endClass(@RequestBody Class classInfo){
        return classService.endClass(classInfo);
    }

    /**
     * @author 221701230张增燊
     * 获取班级、小组列表（注册用）//点注册的时候要知道有哪些班哪些组可以选择
     */
    @GetMapping(value = "register/team")
    public Map<String,Object> listClass(){
        return classService.listClass();
    }
}
