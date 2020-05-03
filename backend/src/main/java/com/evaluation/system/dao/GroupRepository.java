package com.evaluation.system.dao;

import com.evaluation.system.bean.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Team,Integer> {
    public Team findByClassIdAndAndGroupId(int classId, int groupId);
}