package com.evaluation.system.service;

import com.evaluation.system.bean.Class;
import com.evaluation.system.dao.ClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ClassService {

    @Autowired
    ClassRepository classRepository;
    public Map<String,Object> saveClass(Class classInfo,int flag)
    {
        Map<String,Object> result =new HashMap<>();
        try {
            classRepository.save(classInfo);
            if(flag==1) {
                result.put("status","1");
                result.put("msg","创建成功");
            }
            else{
                result.put("status","1");
                result.put("msg","修改成功");
            }
        }
        catch (Exception e)
        {
            if(flag==1){
                result.put("status","0");
                result.put("msg","班级名已存在，或其他信息填写不正确");
            }
            else{
                result.put("status","0");
                result.put("msg","修改失败，请检查是否填写正确");
            }
        }

        return result;
    }

    public Map<String,Object> deleteClass(Class classInfo)
    {
        Map<String,Object> result =new HashMap<String,Object>();
        try {
            classRepository.deleteById(classInfo.getClassId());
            result.put("status","1");
            result.put("msg","删除成功");
        }
        catch (Exception e)
        {
            result.put("status","0");
            result.put("msg","删除失败，检查班级是否存在");
        }

        return result;
    }

    public Map<String,Object> findAllClass()
    {
        Map<String,Object> result =new HashMap<String,Object>();
        try {
            List<Class> resultClass = classRepository.findAll();
            result.put("status","1");
            result.put("date",resultClass);
        }
        catch (Exception e)
        {
            result.put("status","0");
            result.put("msg","查询失败");
        }

        return result;
    }

    public Map<String,Object> endClass(Class classInfo)
    {
        Class classBean = classRepository.findByClassId(classInfo.getClassId());
        Map<String,Object> result =new HashMap<String,Object>();
        try {
            classBean.setStatus("已結束");
            classRepository.save(classBean);
            result.put("status","1");
            result.put("msg","结束成功");
        }
        catch (Exception e)
        {
            result.put("status","0");
            result.put("msg","结束失败，请检查是否存在这个班级");
        }

        return result;
    }


}
