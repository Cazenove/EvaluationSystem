package com.evaluation.system.service;

import com.evaluation.system.bean.SubmitInner;
import com.evaluation.system.dao.SubmitInnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
public class SubmitInnerService {
    @Autowired
    private SubmitInnerRepository submitInnerRepository;

    /**
     * @author 221701230张增燊
     * 查看组内评价表记录
     */
    public Map<String,Object> listSubmitInner(){
        Map<String,Object> map=new HashMap<>();
        try{
            List<SubmitInner> submitInners=submitInnerRepository.findAll();
            map.put("status",1);
            map.put("data",submitInners);
        }catch(Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            map.put("status",0);
            map.put("msg","查询发生错误");
        }
        return map;
    }
}
