package com.evaluation.system.controller;

import com.evaluation.system.dao.AssistantRepository;
import com.evaluation.system.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
public class AdminController {
    @Autowired
    private  AssistantRepository assistantRepository;
    @Autowired
    private AdminService adminService;

    /*管理员添加用户
     * 成功返回true
     * */
    @ResponseBody
    @PostMapping(value = "/admin/user/create")
    public HashMap<String, Object> user_Create(@RequestParam("user_id") String id, @RequestParam("password") String password,
                                                @RequestParam("name") String name, @RequestParam("class_id") int class_id,
                                                @RequestParam("group_id") int group_id, @RequestParam("status") String status,
                                                @RequestParam("telephone") String telephone){
        return adminService.userCreate(id,password,name,class_id,group_id,status,telephone);
    }

    /*管理员删除用户
     * 成功返回true
     * */
    @ResponseBody
    @DeleteMapping(value="/admin/user/delete")
    public HashMap<String, Object> user_Delete(@RequestParam("user_id") String id){
        return adminService.userDelete(id);
    }

    /*管理员修改用户信息
     * 成功返回true
     * */
    @ResponseBody
    @PostMapping(value = "/admin/user/update")
    public HashMap<String,Object> user_Update(@RequestParam("user_id") String id, @RequestParam("password") String password,
                                               @RequestParam("name") String name, @RequestParam("class_id") int class_id,
                                               @RequestParam("group_id") int group_id, @RequestParam("status") String status,
                                               @RequestParam("telephone") String telephone){
        return adminService.userUpdate(id,password,name,class_id,group_id,status,telephone);
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
    @ResponseBody
    @PostMapping(value = "/admin/assistant/create")
    public HashMap<String, Object> assistant_Create(@RequestParam("assistant_id") String id, @RequestParam("password") String password,
                                                @RequestParam("name") String name, @RequestParam("class_id") int class_id,
                                                @RequestParam("telephone") String telephone){
        return adminService.assistantCreate(id,password,name,class_id,telephone);
    }

    /*管理员删除助教
     * 成功返回true
     * */
    @ResponseBody
    @DeleteMapping(value="/admin/assistant/delete")
    public HashMap<String, Object> assistant_Delete(@RequestParam("assistant_id") String id){
        return adminService.assistantDelete(id);
    }

    /*管理员修改用户信息
     * 成功返回true
     * */
    @ResponseBody
    @PostMapping(value = "/admin/assistant/update")
    public HashMap<String,Object> assistant_Update(@RequestParam("assistant_id") String id, @RequestParam("password") String password,
                                               @RequestParam("name") String name, @RequestParam("class_id") int class_id,
                                               @RequestParam("telephone") String telephone){
        return adminService.assistantUpdate(id,password,name,class_id,telephone);
    }

    /*管理员获取助教列表
     * 成功返回true
     * */
    @GetMapping(value = "/admin/assistant/list")
    public HashMap<String,Object> assistant_List(){
        return adminService.assistantList();
    }
}
