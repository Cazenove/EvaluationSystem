package com.evaluation.system.controller;

import com.evaluation.system.bean.GroupScore;
import com.evaluation.system.bean.User;
import com.evaluation.system.bean.Team;
import com.evaluation.system.dao.GroupScoreRepository;
import com.evaluation.system.dao.TeamRepository;
import com.evaluation.system.dao.UserRepository;
import com.evaluation.system.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author 221701230张增燊
 */
@RestController

public class TeamController {
    @Autowired
    private TeamService teamService;

    /**获取所有小组的详细信息**/
    @GetMapping(value="/admin/team/list")
    public Map<String,Object> detailGroup(){
        return teamService.listTeams();
    }

    /**修改小组名称**/
    @PostMapping(value="user/team/update")
    public Map<String, Object> renameGroup(@RequestBody Map<String,Object> content){
        return teamService.updateTeamName(content);
    }

}
