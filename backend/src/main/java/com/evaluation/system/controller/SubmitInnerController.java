package com.evaluation.system.controller;

import com.evaluation.system.bean.SubmitInner;
import com.evaluation.system.bean.SubmitOuter;
import com.evaluation.system.service.SubmitInnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class SubmitInnerController {
    @Autowired
    private SubmitInnerService submitInnerService;

    /**
     * @author 221701230张增燊
     * 查看组内评价表提交记录
     */
    @GetMapping(value="admin/evaluation/inner/submit")
    public Map<String,Object> listSubmitInner(){
        return submitInnerService.listSubmitInner();
    }

    /**
     *10.提交组内评分表
     * @author 221701310_陈家祯
     */
    @PostMapping(value = "/user/evaluation/inner/submit")
    public Map<String, Object> submitEvaluationInner(@RequestBody SubmitInner submitInner){
        return submitInnerService.submitEvaluationInner(submitInner);
    }
}
