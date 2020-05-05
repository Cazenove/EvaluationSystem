package com.evaluation.system.service;

import com.evaluation.system.bean.Admin;
import com.evaluation.system.bean.Assistant;
import com.evaluation.system.bean.Team;
import com.evaluation.system.bean.User;
import com.evaluation.system.dao.AdminRepository;
import com.evaluation.system.dao.AssistantRepository;
import com.evaluation.system.dao.GroupRepository;
import com.evaluation.system.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.HashMap;

/**
 * @author acer
 */
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private AssistantRepository assistantRepository;
    /*登录检查*/
    public HashMap<String,Object> loginCheck(User user){
        String flag ="";
        String msg = "账号或密码错误";
        HashMap<String,Object> result = new HashMap<>(15);
        User user1 = userRepository.findByUserId(user.getUserId());
        Assistant assistant = assistantRepository.findByAssistantId(user.getUserId());
        Admin admin = adminRepository.findByAdminId(user.getUserId());
        if (user1!=null && user1.getPassword().equals(user.getPassword())){
            flag = user1.getStatus();
            result.put("status",flag);
            result.put("data",user1);
        }
        else if (assistant!=null && assistant.getPassword().equals(user.getPassword())){
            result.put("status",3);
            result.put("data",assistant);
        }
        else if (admin!=null && admin.getPassword().equals(user.getPassword())){
            result.put("status",4);
        }
        else {
            result.put("status",flag);
            result.put("msg",msg);
        }
        return  result;
    }

    /*注册检查学号、电话是否注册*/
    @Transactional(rollbackFor = Exception.class)
    public HashMap<String, Object> registerCheck(User user){
        HashMap<String,Object> result = new HashMap<>();
        int flag = 0;
        String msg = "注册成功";
        try {
            User user1 = userRepository.findByUserId(user.getUserId());
            User user2 = userRepository.findByTelephone(user.getTelephone());
            Team team = groupRepository.findByClassIdAndAndGroupId(user.getClassId(),user.getGroupId());
            if (user1==null && user2==null){
                user1.setUserId(user.getUserId());
                user1.setPassword(user.getPassword());
                user1.setName(user.getName());
                user1.setClassId(user.getClassId());
                user1.setGroupId(team.getGroupId());
                user1.setStatus(user.getStatus());
                user1.setTelephone(user.getTelephone());
                userRepository.save(user1);
                flag = 1;
                result.put("status",flag);
                result.put("msg",msg) ;
         }
        }
        catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            msg = "学号或手机号已注册";
            result.put("status",flag);
            result.put("msg",msg) ;
            return  result;
        }

        return  result;
    }

    /*修改个人信息*/
    @Transactional(rollbackFor = Exception.class)
    public HashMap<String,Object> updateCheck(User user2){
        User user = userRepository.findByUserId(user2.getUserId());
        HashMap<String,Object> result = new HashMap<>();
        int flag = 0;
        String msg = "修改成功";
        User user1 = user;
        if (!user2.getPassword().equals("")) {
            user1.setPassword(user2.getPassword());
        }
        if (!user2.getName().equals("")) {
            user1.setName(user2.getName());
        }
        if (!user2.getTelephone().equals("")) {
            user = userRepository.findByTelephone(user2.getTelephone());
            if (user!=null) {
                msg = "手机号已被使用";
            } else {
                user1.setTelephone(user2.getTelephone());
                flag = 1;
            }
        }
        result.put("status",flag);
        result.put("msg",msg);
        userRepository.save(user1);
        return  result;
    }
}
