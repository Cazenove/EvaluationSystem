package com.evaluation.system.service;

import com.evaluation.system.bean.GroupSuggestion;
import com.evaluation.system.bean.SubmitInner;
import com.evaluation.system.bean.SubmitOuter;
import com.evaluation.system.dao.GroupSuggestionRepository;
import com.evaluation.system.dao.SubmitInnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.text.SimpleDateFormat;
import java.util.Date;
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

    /**
     * 提交组内评分表
     * @author 221701310_陈家祯
     */
    public Map<String, Object> submitEvaluationInner(SubmitInner submitInner) {
        int flag = 0;
        String msg = "提交失败";
        HashMap<String,Object> result = new HashMap<>();
        try {
            SubmitInner saveEvaluation = new SubmitInner();
            saveEvaluation.setContent(submitInner.getContent());
            saveEvaluation.setGroupId(submitInner.getGroupId());
            saveEvaluation.setEvaluationInnerId(submitInner.getEvaluationInnerId());
            saveEvaluation.setSubmitTime(submitInner.getSubmitTime());
            submitInnerRepository.save(saveEvaluation);
            flag = 1;
            msg = "提交成功";
            result.put("status",flag);
            result.put("msg",msg);
        }
        catch (Exception e)
        {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            result.put("status",flag);
            result.put("msg",msg);
            return result;
        }
        return result;
    }
}
