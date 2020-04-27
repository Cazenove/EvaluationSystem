package com.evaluation.system.controller;

import com.evaluation.system.bean.Group_suggestion_admin;
import com.evaluation.system.dao.Group_suggestion_adminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/admin/suggestion")
public class Group_suggestion_adminController {
    @Autowired
    private Group_suggestion_adminRepository group_suggestion_adminRepository;

    //新建一个管理员对小组的建议
    @PostMapping(value="/add")
    public Group_suggestion_admin addSuggestion(@RequestParam("admin_id") String admin_id,
                                                @RequestParam("group_id") int group_id,
                                                @RequestParam("content") String content){
        Group_suggestion_admin group_suggestion_admin=new Group_suggestion_admin();
        group_suggestion_admin.setAdmin_id(admin_id);
        group_suggestion_admin.setGroupId(group_id);
        group_suggestion_admin.setContent(content);

        return group_suggestion_adminRepository.save(group_suggestion_admin);
    }

    //删除一条管理员对小组的建议
    @DeleteMapping(value="/delete")
    public void deleteSuggestion(@RequestParam("group_suggestion_admin_id") Integer group_suggestion_admin_id){

        group_suggestion_adminRepository.deleteById(group_suggestion_admin_id);
    }

    //修改一条管理员对小组的建议
    @PutMapping(value="/update")
    public Group_suggestion_admin updateSuggestion(@RequestParam("group_suggestion_admin_id") int id,
                                                   @RequestParam("admin_id") String admin_id,
                                                   @RequestParam("group_id") int group_id,
                                                   @RequestParam("content") String content){
        Group_suggestion_admin group_suggestion_admin=new Group_suggestion_admin();
        group_suggestion_admin.setGroup_suggestion_admin_id(id);
        group_suggestion_admin.setAdmin_id(admin_id);
        group_suggestion_admin.setContent(content);
        group_suggestion_admin.setGroupId(group_id);

        return group_suggestion_adminRepository.save(group_suggestion_admin);
    }

    //查询一条建议,并没有做得很好
    @GetMapping(value="search")
    public  List<Group_suggestion_admin> findSuggestion(@RequestParam("admin_id") String admin_id,
                                                       @RequestParam("group_id") Integer group_id,
                                                       @RequestParam("content") String content){

        return group_suggestion_adminRepository.findByGroupId(group_id);

    }


}
