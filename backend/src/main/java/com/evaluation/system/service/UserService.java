package com.evaluation.system.service;

import com.evaluation.system.bean.User;
import com.evaluation.system.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @author acer
 */
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    /*登录检查*/
    public boolean loginCheck(String id,String password){
        boolean flag = false;
        User user = userRepository.findByUserId(id);
        if (user!=null){
            user.getPassword().equals(password);
            flag = true;
        }
        return  flag;
    }

    /*注册检查学号、电话是否注册*/
    public HashMap<String, Object> registerCheck(String id, String password, String name, int classId, int groupId, String status,
                                                 String tel){
        HashMap<String,Object> result = new HashMap<>();
        int flag = 0;
        String msg = "注册成功";
        User user = userRepository.findByUserId(id);
        User user2 = userRepository.findByTelephone(tel);
        if (user==null && user2==null){
            User user1 = new User();
            user1.setUser_id(id);
            user1.setPassword(password);
            user1.setName(name);
            user1.setClass_id(classId);
            user1.setGroupId(groupId);
            user1.setStatus(status);
            user1.setTelephone(tel);
            userRepository.save(user1);
            flag = 1;

        }
        else {
            msg = "学号或手机号已注册";
        }
        result.put("status",flag);
        result.put("msg",msg) ;
        return  result;
    }

    /*修改个人信息*/
    public HashMap<String,Object> updateCheck(String id,String password,String name,String tel){
        User user = userRepository.findByUserId(id);
        HashMap<String,Object> result = new HashMap<>();
        int flag = 0;
        String msg = "修改成功";
        User user1 = user;
        if (!password.equals("")) {
            user1.setPassword(password);
        }
        if (!name.equals("")) {
            user1.setName(name);
        }
        if (!tel.equals("")) {
            user = userRepository.findByTelephone(tel);
            if (user!=null) {
                msg = "手机号已被使用";
            } else {
                user1.setTelephone(tel);
                flag = 1;
            }
        }
        result.put("status",flag);
        result.put("msg",msg);
        userRepository.save(user1);
        return  result;
    }
}
