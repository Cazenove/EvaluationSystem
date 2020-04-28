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
    public Map<String,Object> saveClass(Class classInfo)
    {
        Map<String,Object> result =new HashMap<>();
        try {
            classRepository.save(classInfo);
            result.put("status","true");
        }
        catch (Exception e)
        {
            result.put("status","false");
        }

        return result;
    }

    public Map<String,Object> deleteClass(Class classInfo)
    {
        Map<String,Object> result =new HashMap<String,Object>();
        try {
            classRepository.deleteById(classInfo.getClassId());
            result.put("status","true");
        }
        catch (Exception e)
        {
            result.put("status","false");
        }

        return result;
    }

    public Map<String,Object> findAllClass()
    {
        Map<String,Object> result =new HashMap<String,Object>();
        try {
            List<Class> resultClass = classRepository.findAll();
            result.put("status","true");
            result.put("date",resultClass);
        }
        catch (Exception e)
        {
            result.put("status","false");
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
            result.put("status","true");
        }
        catch (Exception e)
        {
            result.put("status","false");
        }

        return result;
    }


}
