package com.evaluation.system.service;

import com.evaluation.system.bean.User;
import com.evaluation.system.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public boolean registerCheck(String id,String password,String name,int class_id,int group_id,String status,
                                 String tel){
        boolean flag = false;
        User user = userRepository.findByUserId(id);
        if (user==null){
            User user1 = new User();
            user1.setUser_id(id);
            user1.setPassword(password);
            user1.setName(name);
            user1.setClass_id(class_id);
            user1.setGroupId(group_id);
            user1.setStatus(status);
            user1.setTelephone(tel);
            userRepository.save(user1);
            flag = true;
        }
        return  flag;
    }

    /*修改个人信息*/
    public boolean updateCheck(String id,String password,String name,String tel){
        User user = userRepository.findByUserId(id);
        String oldPasswprd = user.getPassword();
        String oldName = user.getName();
        String oldTel = user.getTelephone();
        User user1 = user;
        user1.setUser_id(id);
        if (!password.equals("")) user1.setPassword(password);
        if (!name.equals("")) user1.setName(name);
        if (!tel.equals("")) user1.setTelephone(tel);
        userRepository.save(user1);
        return  true;
    }
}
