package com.evaluation.system.dao;

import com.evaluation.system.bean.GroupScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author 22701230张增燊
 */
public interface GroupScoreRepository extends JpaRepository<GroupScore,Integer> {
    /**通过小组id来寻找小组历次分数
     * @param groupId 小组id
     * @return GroupScore
     **/
    public GroupScore findByGroupId(int groupId);

    /**
     * @author 221701310_陈家祯
     */
    @Query("SELECT a FROM GroupScore a WHERE a.evaluationOuterId = :evaluationOuterId")
    public GroupScore findOneByEvaluationOuterId(@Param("evaluationOuterId")int evaluationOuterId);

    /**
     *
     * @param evaluationOuterId
     * @param groupId
     * @return GroupScore
     */
    @Query("SELECT a FROM GroupScore a WHERE a.evaluationOuterId = :evaluationOuterId and a.groupId = :groupId")
    public GroupScore findOneByIdAndGourp(@Param("evaluationOuterId")int evaluationOuterId ,@Param("groupId")int groupId);
}
