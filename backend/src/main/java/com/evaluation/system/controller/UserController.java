package com.evaluation.system.controller;

import com.evaluation.system.bean.User;
import com.evaluation.system.dao.UserRepository;
import com.evaluation.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    /*用户登录
    *@成功返回true
    * */
    @PostMapping(value = "/login")
    public HashMap<String, Object> login(@RequestBody User user){
        return  userService.loginCheck(user);
    }

    /*用户注册
    * @成功返回true
    * */
    @ResponseBody
    @PostMapping(value = "/register")
    public HashMap<String, Object> register(@RequestBody User user){
        return userService.registerCheck(user);
    }

    /*修改个人信息
    * 成功返回true
    * */
    @ResponseBody
    @PostMapping(value = "/user/update")
    public HashMap<String,Object> update(@RequestBody User user){
        return userService.updateCheck(user);
    }
}
