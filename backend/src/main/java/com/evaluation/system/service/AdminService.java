package com.evaluation.system.service;

import com.evaluation.system.bean.Assistant;
import com.evaluation.system.bean.User;
import com.evaluation.system.dao.AssistantRepository;
import com.evaluation.system.dao.ClassRepository;
import com.evaluation.system.dao.GroupRepository;
import com.evaluation.system.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class AdminService {
    @Autowired
    private AssistantRepository assistantRepository;
    @Autowired
    private ClassRepository classRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GroupRepository groupRepository;

    /**
     * @author 1890
     */
    /*管理员添加用户*/
    public HashMap<String, Object> userCreate(User userInfo){
        int flag = 0;
        String msg = "添加成功";
        HashMap<String,Object> result = new HashMap<>();
        User user = userRepository.findByUserId(userInfo.getUserId());
        User user2 = userRepository.findByTelephone(userInfo.getTelephone());
        List<User> user4 = userRepository.findByGroupIdAndStatus(userInfo.getGroupId(),"2");
        if(userInfo.getStatus().equals("2") && user4 != null)
        {
            result.put("status",flag);
            result.put("msg","组长已经存在");
            return result;
        }
        else if (user==null){
            if(user2==null){
                User user1 = new User();
                if (classRepository.findByClassId(userInfo.getClassId()) == null) {
                    msg = "班级不存在";
                } else {
                    if(groupRepository.findByGroupId(userInfo.getGroupId())==null){
                        msg = "组不存在";
                    }
                    else{
                        user1.setUserId(userInfo.getUserId());

                        //输入密码加密,密文为 md5Password
                        String md5Password = DigestUtils.md5DigestAsHex(userInfo.getPassword().getBytes());
                        user1.setPassword(md5Password);

                        user1.setName(userInfo.getName());
                        user1.setClassId(userInfo.getClassId());
                        user1.setGroupId(userInfo.getGroupId());
                        user1.setStatus(userInfo.getStatus());
                        user1.setTelephone(userInfo.getTelephone());
                        userRepository.save(user1);
                        flag = 1;
                    }
                }
            }else{
                msg = "手机号已注册";
            }
        }
        else {
            msg = "学号已注册";
        }
        result.put("status",flag);
        result.put("msg",msg) ;
        return  result;
    }

    /*管理员删除用户*/
    public HashMap<String, Object> userDelete(User userInfo) {
        HashMap<String,Object> result = new HashMap<>();
        int flag = 0;
        String msg = "删除成功";
        User user = userRepository.findByUserId(userInfo.getUserId());
        if(user==null){
            msg = "该用户不存在";
        }
        else{
            userRepository.deleteById(userInfo.getUserId());
            flag=1;
        }
        result.put("status",flag);
        result.put("msg",msg) ;
        return  result;
    }

    /*管理员修改用户信息*/
    public HashMap<String,Object> userUpdate(User userInfo){
        HashMap<String,Object> result = new HashMap<>();
        int flag = 0;
        String msg = "修改成功";
        User user = userRepository.findByUserId(userInfo.getUserId());
        List<User> user4 = userRepository.findByGroupIdAndStatus(userInfo.getGroupId(),"2");
        if(userInfo.getStatus().equals("2"))
        {
            if(user4!=null)
            {
                if(!userInfo.getUserId().equals(user4.get(0).getUserId()))
                {
                    result.put("status",flag);
                    result.put("msg","组长已经存在，修改失败");
                    return result;
                }
            }
        }
        if(user!=null) {
            User user1 = user;
            User user2 = userRepository.findByTelephone(userInfo.getTelephone());
            if (!userInfo.getPassword().equals( "" )&&!userInfo.getName().equals( "" )&&!userInfo.getStatus().equals( "" )) {

                //输入密码加密,密文为 md5Password
                String md5Password = DigestUtils.md5DigestAsHex(userInfo.getPassword().getBytes());
                user1.setPassword(md5Password);

                user1.setName(userInfo.getName());
                user1.setStatus(userInfo.getStatus());
                if (classRepository.findByClassId(userInfo.getClassId()) == null) {
                    msg = "班级不存在";
                } else {
                    user1.setClassId(userInfo.getClassId());
                    if(groupRepository.findByGroupId(userInfo.getGroupId(  ))==null){
                        msg = "组不存在";
                    }
                    else{
                        user1.setGroupId(userInfo.getGroupId());
                        if (!userInfo.getTelephone().equals( "" )) {
                            user1.setTelephone(userInfo.getTelephone());
                            flag = 1;
                        }
                    }
                }
            }
            else{
                msg="输入不为空";
            }
            if(flag==1){
                userRepository.save(user1);
            }
        }
        else{
            msg = "账号不存在";
        }
        result.put("status",flag);
        result.put("msg",msg);
        return  result;
    }

    /*管理员获取用户列表*/
    public HashMap<String,Object> userList(){
        HashMap<String,Object> result = new HashMap<>();
        List<User> list = userRepository.findAll();
        if (!list.isEmpty()){
            result.put("status",1);
            result.put("data",list);
        }
        else {
            result.put("status",0);
            result.put("msg","列表为空，获取失败");
        }
        return result;
    }

    /*管理员添加助教*/
    public HashMap<String, Object> assistantCreate(Assistant assistantInfo){
        int flag = 0;
        String msg = "添加成功";
        HashMap<String,Object> result = new HashMap<>();
        Assistant assistant = assistantRepository.findByAssistantId(assistantInfo.getAssistantId());
        Assistant assistant2 = assistantRepository.findByTelephone(assistantInfo.getTelephone());
        if (assistant==null&&assistant2==null){
            Assistant assistant1 = new Assistant();
            if (classRepository.findByClassId(assistantInfo.getClassId()) == null) {
                msg = "班级不存在";
            } else {
                assistant1.setAssistantId(assistantInfo.getAssistantId());

                //输入密码加密,密文为 md5Password
                String md5Password = DigestUtils.md5DigestAsHex(assistantInfo.getPassword().getBytes());
                assistant1.setPassword(md5Password);

                assistant1.setName(assistantInfo.getName());
                assistant1.setClassId(assistantInfo.getClassId());
                assistant1.setTelephone(assistantInfo.getTelephone());
                assistantRepository.save(assistant1);
                flag = 1;
            }

        }
        else {
            msg = "账号或手机号已注册";
        }
        result.put("status",flag);
        result.put("msg",msg) ;
        return  result;
    }

    /*管理员删除助教*/
    public HashMap<String, Object> assistantDelete(Assistant assistantInfo) {
        HashMap<String,Object> result = new HashMap<>();
        int flag = 0;
        String msg = "删除成功";
        Assistant assistant = assistantRepository.findByAssistantId(assistantInfo.getAssistantId());
        if(assistant==null){
            msg = "该助教不存在";
        }
        else{
            assistantRepository.deleteById(assistantInfo.getAssistantId());
            flag=1;
        }
        result.put("status",flag);
        result.put("msg",msg) ;
        return  result;
    }

    /*管理员修改助教信息*/
    public HashMap<String,Object> assistantUpdate(Assistant assistantInfo){
        HashMap<String,Object> result = new HashMap<>();
        int flag = 0;
        String msg = "修改成功";
        if(assistantRepository.findByAssistantId(assistantInfo.getAssistantId())!=null) {
            Assistant assistant=assistantRepository.findByAssistantId(assistantInfo.getAssistantId());
            Assistant assistant1=assistant;
            if (!assistantInfo.getName().equals( "" )) {
                if(!assistantInfo.getPassword().equals( "" )){
                    assistant1.setName(assistantInfo.getName());

                    //输入密码加密,密文为 md5Password
                    String md5Password = DigestUtils.md5DigestAsHex(assistantInfo.getPassword().getBytes());
                    assistant1.setPassword(md5Password);
                    
                    if (classRepository.findByClassId(assistantInfo.getClassId()) == null) {
                        msg = "班级不存在";
                    } else {
                        if (assistantInfo.getTelephone().equals( "" )) {
                            msg = "手机号不能为空";
                        } else{
                            assistant1.setTelephone(assistantInfo.getTelephone());
                            flag = 1;
                        }
                    }
                }
                else{
                    msg = "密码不为空";
                }
            }
            else{
                msg = "姓名不为空";
            }
            if(flag==1){
                assistantRepository.save(assistant1);
            }
        }
        else{
            msg = "账号不存在";
        }
        result.put("status",flag);
        result.put("msg",msg);
        return  result;
    }

    /*管理员获取助教列表*/
    public HashMap<String,Object> assistantList(){
        HashMap<String,Object> result = new HashMap<>();
        List<Assistant> list = assistantRepository.findAll();
        if (!list.isEmpty()){
            result.put("status",1);
            result.put("data",list);
        }
        else {
            result.put("status",0);
            result.put("msg","列表为空，获取失败");
        }
        return result;
    }
}

