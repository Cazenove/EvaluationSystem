package com.evaluation.system.service;

import com.evaluation.system.bean.Assistant;
import com.evaluation.system.bean.User;
import com.evaluation.system.dao.AssistantRepository;
import com.evaluation.system.dao.ClassRepository;
import com.evaluation.system.dao.GroupRepository;
import com.evaluation.system.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public HashMap<String, Object> userCreate(String id, String password, String name, int classId, int groupId, String status,
                                               String tel){
        int flag = 0;
        String msg = "添加成功";
        HashMap<String,Object> result = new HashMap<>();
        User user = userRepository.findByUserId(id);
        User user2 = userRepository.findByTelephone( tel );
        if (user==null){
            if(user2==null){
                User user1 = new User();
                if (classRepository.findByClassId( classId ) == null) {
                    msg = "班级不存在";
                } else {
                    if(groupRepository.findByGroupId(groupId)==null){
                        msg = "组不存在";
                    }
                    else{
                        user1.setUserId(id);
                        user1.setPassword(password);
                        user1.setName(name);
                        user1.setClassId(classId);
                        user1.setGroupId(groupId);
                        user1.setStatus(status);
                        user1.setTelephone(tel);
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
    public HashMap<String, Object> userDelete(String id) {
        HashMap<String,Object> result = new HashMap<>();
        int flag = 0;
        String msg = "删除成功";
        User user = userRepository.findByUserId(id);
        if(user==null){
            msg = "该用户不存在";
        }
        else{
            userRepository.deleteById( id );
            flag=1;
        }
        result.put("status",flag);
        result.put("msg",msg) ;
        return  result;
    }

    /*管理员修改用户信息*/
    public HashMap<String,Object> userUpdate(String id, String password, String name, int classId, int groupId, String status,
                                              String tel){
        HashMap<String,Object> result = new HashMap<>();
        int flag = 0;
        String msg = "修改成功";
        User user = userRepository.findByUserId(id);
        if(user!=null) {
            User user1 = user;
            User user2 = userRepository.findByTelephone( tel );
            if (!password.equals( "" )&&!name.equals( "" )&&!status.equals( "" )) {
                user1.setPassword( password );
                user1.setName( name );
                user1.setStatus( status );
                if (classRepository.findByClassId( classId ) == null) {
                    msg = "班级不存在";
                } else {
                    user1.setClassId( classId );
                    if(groupRepository.findByGroupId(groupId)==null){
                        msg = "组不存在";
                    }
                    else{
                        user1.setGroupId( groupId );
                        if (!tel.equals( "" )) {
                            user1.setTelephone( tel );
                            flag = 1;
                        }
                    }
                }
            }
            else{
                msg="输入不为空";
            }
            if(flag==1)
                userRepository.save(user1);
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
    public HashMap<String, Object> assistantCreate(String id, String password, String name, int classId,String tel){
        int flag = 0;
        String msg = "添加成功";
        HashMap<String,Object> result = new HashMap<>();
        Assistant assistant = assistantRepository.findByAssistantId( id );
        Assistant assistant2 = assistantRepository.findByTelephone(tel);
        if (assistant==null&&assistant2==null){
            Assistant assistant1 = new Assistant();
            if (classRepository.findByClassId( classId ) == null) {
                msg = "班级不存在";
            } else {
                assistant1.setAssistantId( id );
                assistant1.setPassword(password);
                assistant1.setName(name);
                assistant1.setClassId(classId);
                assistant1.setTelephone(tel);
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
    public HashMap<String, Object> assistantDelete(String id) {
        HashMap<String,Object> result = new HashMap<>();
        int flag = 0;
        String msg = "删除成功";
        Assistant assistant = assistantRepository.findByAssistantId(id);
        if(assistant==null){
            msg = "该助教不存在";
        }
        else{
            assistantRepository.deleteById( id );
            flag=1;
        }
        result.put("status",flag);
        result.put("msg",msg) ;
        return  result;
    }

    /*管理员修改助教信息*/
    public HashMap<String,Object> assistantUpdate(String id, String password, String name, int classId, String tel){
        HashMap<String,Object> result = new HashMap<>();
        int flag = 0;
        String msg = "修改成功";
        if(assistantRepository.findByAssistantId( id )!=null) {
            Assistant assistant=assistantRepository.findByAssistantId( id );
            Assistant assistant1=assistant;
            if (!name.equals( "" )) {
                if(!password.equals( "" )){
                    assistant.setName( name );
                    assistant.setPassword(password);
                    if (classRepository.findByClassId( classId ) == null) {
                        msg = "班级不存在";
                    } else {
                        if (tel.equals( "" )) {
                            msg = "手机号不能为空";
                        } else{
                            assistant1.setTelephone( tel );
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
            if(flag==1)
                assistantRepository.save(assistant1);
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

