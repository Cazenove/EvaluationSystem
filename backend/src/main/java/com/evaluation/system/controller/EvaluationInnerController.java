package com.evaluation.system.controller;

import com.evaluation.system.bean.EvaluationInner;
import com.evaluation.system.service.EvaluationInnerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 221701310_陈家祯
 */
@RestController
public class EvaluationInnerController {

    @Resource
    private EvaluationInnerService evaluationInnerService;

    /**
     * 8.获取组内评分表列表
     * @author 221701310_陈家祯
     */
    @GetMapping(value = "/user/evaluation/inner/list")
    public Map<String, Object> evaluationInnerFindAll(@RequestParam("classId") int classId) {
        return evaluationInnerService.getEvaluationInnersByClassId(classId);
    }

    /**
     * 9.获取一份组内评分表内容
     * @author 221701310_陈家祯
     */
    @GetMapping(value = "/user/evaluation/inner")
    public Map<String, Object> evaluationInnerFindOne(@RequestParam("evaluationInnerId") Integer id,
                                                      @RequestParam("classId") Integer classId,
                                                      @RequestParam("groupId") Integer groupId) {
        return evaluationInnerService.getOneEvaluationInner(id,classId,groupId);
    }

}
