package com.evaluation.system.controller;

import com.evaluation.system.bean.Admin;
import com.evaluation.system.dao.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
//一个例子
@RestController
public class AdminController {
    @Autowired
    private AdminRepository adminRepository;

    //列出所有管理员
    @GetMapping(value="admins")
    public List<Admin> listAdmin(){
        return adminRepository.findAll();
    }
}
