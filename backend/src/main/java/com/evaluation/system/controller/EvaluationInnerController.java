package com.evaluation.system.controller;

/**
 * @author 221701310_陈家祯
 */

import com.evaluation.system.bean.EvaluationInner;
import com.evaluation.system.service.EvaluationInnerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class EvaluationInnerController {

    @Resource
    private EvaluationInnerService evaluationInnerService;

    /**8.获取组内评分表列表**/
    @GetMapping(value = "/user/evaluation/inner/list")
    public Map<String, Object> innerFindAll(@RequestParam("classId") int classId) {
        return evaluationInnerService.getEvaluationInnerByClassId(classId);
    }


}
