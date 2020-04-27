package com.evaluation.system.controller;

import com.evaluation.system.bean.Group;
import com.evaluation.system.bean.User;
import com.evaluation.system.dao.GroupRepository;
import com.evaluation.system.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value="/admin/group")
public class GroupController {
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping(value="detail")
    public List<Group> detailGroup(){
        List<Group> groups=groupRepository.findAll();
        for(int i=0;i<groups.size();i++)
        {
            int group_id=groups.get(i).getGroup_id();
            List<User> members=userRepository.findByGroupId(group_id);
            groups.get(i).setMembers(members);
            for(int j=0;j<members.size();j++)
            {
                if(members.get(j).getStatus().contentEquals("组长"))
                {
                    groups.get(i).setLeader(members.get(j));
                }
            }
        }
        return groups;

    }
}
