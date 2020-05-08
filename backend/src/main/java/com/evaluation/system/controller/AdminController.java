package com.evaluation.system.controller;

import com.evaluation.system.bean.Assistant;
import com.evaluation.system.bean.User;
import com.evaluation.system.dao.AssistantRepository;
import com.evaluation.system.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
public class AdminController {
    /**
     * @author 221701324
     * 管理员操作
     */


    @Autowired
    private  AssistantRepository assistantRepository;
    @Autowired
    private AdminService adminService;

    /*管理员添加用户
     * 成功返回true
     * */
    @PostMapping(value = "/admin/user/create")
    public HashMap<String, Object> user_Create(@RequestBody User userInfo){
        return adminService.userCreate(userInfo);
    }

    /*管理员删除用户
     * 成功返回true
     * */
    @PostMapping(value="/admin/user/delete")
    public HashMap<String, Object> user_Delete(@RequestBody User userInfo){
        return adminService.userDelete(userInfo);
    }

    /*管理员修改用户信息
     * 成功返回true
     * */
    @PostMapping(value = "/admin/user/update")
    public HashMap<String,Object> user_Update(@RequestBody User userInfo){
        return adminService.userUpdate(userInfo);
    }

    /*管理员获取用户列表
     * 成功返回true
     * */
    @GetMapping(value = "/admin/user/list")
    public HashMap<String,Object> user_List(){
        return adminService.userList();
    }

    /*管理员添加助教
     * 成功返回true
     * */
    @PostMapping(value = "/admin/assistant/create")
    public HashMap<String, Object> assistant_Create(@RequestBody Assistant assistantInfo){
        return adminService.assistantCreate(assistantInfo);
    }

    /*管理员删除助教
     * 成功返回true
     * */
    @ResponseBody
    @PostMapping(value="/admin/assistant/delete")
    public HashMap<String, Object> assistant_Delete(@RequestBody Assistant assistantInfo){
        return adminService.assistantDelete(assistantInfo);
    }

    /*管理员修改用户信息
     * 成功返回true
     * */
    @ResponseBody
    @PostMapping(value = "/admin/assistant/update")
    public HashMap<String,Object> assistant_Update(@RequestBody Assistant assistantInfo){
        return adminService.assistantUpdate(assistantInfo);
    }

    /*管理员获取助教列表
     * 成功返回true
     * */
    @GetMapping(value = "/admin/assistant/list")
    public HashMap<String,Object> assistant_List(){
        return adminService.assistantList();
    }
}
