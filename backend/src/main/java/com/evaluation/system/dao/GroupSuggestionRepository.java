package com.evaluation.system.dao;

import com.evaluation.system.bean.GroupSuggestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Repository
public interface GroupSuggestionRepository extends JpaRepository<GroupSuggestion,Integer> {
    /**修改建议的函数
     * @param groupSuggestionId
     * @return GroupSuggestion
     * **/
    public GroupSuggestion findByGroupSuggestionId(int groupSuggestionId);

    /**
     * 通过groupId来获取小组收到的建议列表
     * @param groupId 小组id
     * @return ArrayList<GroupSuggestion>
     * @author 221701310_陈家祯
     */
    @Query("SELECT a FROM GroupSuggestion a WHERE a.groupId = :groupId")
    public ArrayList<GroupSuggestion> findByGroupId(@Param("groupId") Integer groupId);
}
