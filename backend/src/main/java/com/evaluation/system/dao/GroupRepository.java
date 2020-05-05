package com.evaluation.system.dao;

import com.evaluation.system.bean.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GroupRepository extends JpaRepository<Team,Integer> {
    @Query("SELECT a from Team a where a.classId = :classId and  a.groupNum = :groupNum")
    public Team findByClassIdAndAndGroupNum(@Param("classId") Integer classId, @Param("groupNum") int groupNum);
}