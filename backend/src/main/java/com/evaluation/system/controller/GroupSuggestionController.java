package com.evaluation.system.controller;

import com.evaluation.system.service.GroupSuggestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author 221701230张增燊
 */
@RestController
public class GroupSuggestionController {
    @Autowired
    private GroupSuggestionService groupSuggestionService;

    @PostMapping(value="admin/suggestion/create")
    public Map<String,Object> addSuggestion(@RequestBody Map<String,Object> content){
        return groupSuggestionService.createSuggestion(content);
    }

    @PostMapping(value="admin/suggestion/delete")
    public Map<String,Object> deleteSuggestion(@RequestBody Map<String,Object> content){
        return groupSuggestionService.deleteSuggestion(content);
    }

    /**修改一条对小组的建议**/
    @PostMapping(value="admin/suggestion/update")
    public Map<String,Object> updateSuggestion(@RequestBody Map<String,Object> content){
       return groupSuggestionService.updateSuggestion(content);
    }

    /**列出所有建议**/
    @GetMapping(value="admin/suggestion/list")
    public Map<String,Object> findSuggestion(){

        return groupSuggestionService.listSuggestion();

    }

}
