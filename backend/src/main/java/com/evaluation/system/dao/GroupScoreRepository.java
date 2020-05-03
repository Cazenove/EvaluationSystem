package com.evaluation.system.dao;

import com.evaluation.system.bean.GroupScore;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 22701230张增燊
 */
public interface GroupScoreRepository extends JpaRepository<GroupScore,Integer> {
    /**通过小组id来寻找小组历次分数
     * @param groupId 小组id
     * @return GroupScore
     **/
    public GroupScore findByGroupId(int groupId);
}
