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
 * @author 221701310_陈家祯&221701230张增燊
 */
@RestController

public class TeamController {
    @Autowired
    private TeamService teamService;

    /**
     * @author 221701230张增燊
     *获取所有小组的详细信息
     */
    @GetMapping(value="/admin/team/list")
    public Map<String,Object> detailGroup(){
        return teamService.listTeams();
    }

    /**
     * @author 221701230张增燊
     * 修改小组名称
     */
    @PostMapping(value="user/team/update")
    public Map<String, Object> renameGroup(@RequestBody Map<String,Object> content){
        return teamService.updateTeamName(content);
    }

    /**
     *11.查看小组详情
     * @author 221701310_陈家祯
     */
    @GetMapping(value = "/user/group/details")
    public Map<String, Object> teamInfo(@RequestParam("groupId") Integer groupId) {
        return teamService.getGroupInfo(groupId);
    }

}
