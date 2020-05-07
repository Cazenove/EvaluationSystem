package com.evaluation.system.service;

import com.evaluation.system.bean.EvaluationOuter;
import com.evaluation.system.bean.GroupSuggestion;
import com.evaluation.system.bean.SubmitOuter;
import com.evaluation.system.dao.EvaluationOuterRepository;
import com.evaluation.system.dao.GroupSuggestionRepository;
import com.evaluation.system.dao.SubmitOuterRepository;
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
public class SubmitOuterService {
    @Autowired
    private SubmitOuterRepository submitOuterRepository;
    @Autowired
    private GroupSuggestionRepository groupSuggestionRepository;
    @Autowired
    private EvaluationOuterRepository evaluationOuterRepository;

    /*获取组间评价表内容*/
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> showEvaluationOuter(SubmitOuter submitOuter){
        Map<String,Object> result = new HashMap<>();
        String flag = "0";
        String msg = "获取失败，请检查评价表号";
        try {
            SubmitOuter getSubmitOuter = submitOuterRepository.findByEvaluationOuterIdAndGroupId(submitOuter.getEvaluationOuterId(),
                    submitOuter.getGroupId());
            if (getSubmitOuter!=null) {
                result.put("status", 1);
                result.put("data", getSubmitOuter);
            }
            else {
                EvaluationOuter evaluationOuter = evaluationOuterRepository.findAllByClassIdAndEvaluationOuterId(
                        submitOuter.getClassId(),submitOuter.getEvaluationOuterId());
                result.put("status",1);
                result.put("data",evaluationOuter);
            }
        }
        catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            result.put("status", 0);
            result.put("msg", msg);
            return  result;
        }
        return result;
    }

    /*提交组间评价表*/
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> submitEvaluationOuter(SubmitOuter submitOuter){
        int flag = 0;
        String msg = "提交失败";
        HashMap<String,Object> result = new HashMap<>();
        try {
            SubmitOuter saveEvaluation = new SubmitOuter();
            saveEvaluation.setContent(submitOuter.getContent());
            saveEvaluation.setGroupId(submitOuter.getGroupId());
            saveEvaluation.setEvaluationOuterId(submitOuter.getEvaluationOuterId());
            saveEvaluation.setSubmitTime(submitOuter.getSubmitTime());
            if (submitOuterRepository.save(saveEvaluation).getContent() != null) {
                flag = 1;
                msg = "提交成功";
            }
                //提交suggestions至GroupSuggestion表
                List<List> list = (List<List>) submitOuter.getContent().get("tableData");
                for (int i = 0; i < list.size(); i++) {
                    String str = (String) list.get(i).get(6);
                    int id = (int) list.get(i).get(0);
                    GroupSuggestion groupSuggestion = new GroupSuggestion();
                    groupSuggestion.setSuggestion(str);
                    groupSuggestion.setEvaluationOuterId(submitOuter.getEvaluationOuterId());
                    groupSuggestion.setGroupId(id);
                    groupSuggestionRepository.save(groupSuggestion);
                }
                result.put("status", flag);
                result.put("msg", msg);

        }
        catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                result.put("status",flag);
                result.put("msg",msg);
            return result;
        }
        return result;
    }
    /**
     * @author 221701230张增燊
     * 查看组间评价表记录
     */
    @Transactional(rollbackFor = Exception.class)
    public Map<String,Object> listSubmitOuter(){
        Map<String,Object> map=new HashMap<>();
        try{
            List<SubmitOuter> submitOuters=submitOuterRepository.findAll();
            map.put("status",1);
            map.put("data",submitOuters);
        }catch(Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            map.put("status",0);
            map.put("msg","查询发生错误");
        }
        return map;
    }
}
