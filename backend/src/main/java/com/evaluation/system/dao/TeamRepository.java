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
    /**通过小组id找到小组
     * @author 221701230张增燊
     * @param groupId 小组id
     * @return Team
     */
    public Team findByGroupId(Integer groupId);

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
}
