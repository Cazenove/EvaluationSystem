package com.evaluation.system.dao;

import com.evaluation.system.bean.GroupCompositeScore;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 221701230张增燊
 */
public interface GroupCompositeScoreRepository extends JpaRepository<GroupCompositeScore,Integer> {
    /**通过组号找到小组综合得分
     * @author 221701230张增燊
     * @param groupId 学号
     * @return GroupCompositeScore
     */
    public GroupCompositeScore findByGroupId(int groupId);
}
