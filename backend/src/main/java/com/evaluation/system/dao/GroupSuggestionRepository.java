package com.evaluation.system.dao;

import com.evaluation.system.bean.GroupSuggestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface GroupSuggestionRepository extends JpaRepository<GroupSuggestion,Integer> {
    /**修改建议的函数
     * @param groupSuggestionId
     * @return GroupSuggestion
     * **/
    public GroupSuggestion findByGroupSuggestionId(int groupSuggestionId);

}
