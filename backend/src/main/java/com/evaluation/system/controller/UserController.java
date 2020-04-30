package com.evaluation.system.controller;

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
    *成功返回true
    * */
    /*@PostMapping(value = "/login")
    public boolean login(@RequestParam("user_id") String id,@RequestParam("password") String password){
        return  userService.loginCheck(id,password);
    }*/

    /*用户注册
    * 成功返回true
    * */
    @ResponseBody
    @PostMapping(value = "/register")
    public HashMap<String, Object> register(@RequestParam("user_id") String id, @RequestParam("password") String password,
                                            @RequestParam("name") String name, @RequestParam("class_id") int class_id,
                                            @RequestParam("group_id") int group_id, @RequestParam("status") String status,
                                            @RequestParam("telephone") String telephone){
        return userService.registerCheck(id,password,name,class_id,group_id,status,telephone);
    }

    /*修改个人信息
    * 成功返回true
    * */
    @ResponseBody
    @PostMapping(value = "/update/{user_id}")
    public HashMap<String,Object> update(@PathVariable("user_id") String id, @RequestParam("password") String password,
                          @RequestParam("name") String name, @RequestParam("telephone") String telephone){
        return userService.updateCheck(id,password,name,telephone);
    }
}
