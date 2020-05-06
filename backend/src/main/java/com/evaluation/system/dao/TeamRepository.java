package com.evaluation.system.dao;

import com.evaluation.system.bean.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


/**
 * @author 221701230张增燊&221701310_陈家祯
 */
public interface TeamRepository extends JpaRepository<Team,Integer> {

    /**通过班级id找到小组
     * @author 221701230张增燊
     * @param classId 班级id
     * @return List<Team>
     */
    public List<Team> findByClassId(Integer classId);

    /**
     * @author 221701310_陈家祯
     */
    @Query("SELECT a FROM Team a WHERE a.groupId = :groupId")
    public Team findOneByGroupId(@Param("groupId") Integer groupId);

    /**
     * 组ID查询
     */
    @Query("select g from Team as g where g.groupId = :group_id")
    Team findByGroupId(@Param("group_id") Integer group_id);
}
