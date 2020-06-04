package com.evaluation.system.dao;

import com.evaluation.system.bean.GroupSuggestion;
import com.evaluation.system.bean.SubmitOuter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;

public interface SubmitOuterRepository extends JpaRepository<SubmitOuter,Integer> {

    @Query("SELECT a FROM SubmitOuter a where a.evaluationOuterId = :evaluationOuterId and a.groupId = :groupId")
    public SubmitOuter findByEvaluationOuterIdAndGroupId(@Param("evaluationOuterId") Integer evaluationOuterId,
                                                               @Param("groupId") int groupId);

    @Query("SELECT a FROM SubmitOuter a WHERE a.evaluationOuterId = :evaluationOuterId")
    public ArrayList<SubmitOuter> findByEvaluationOuterId(@Param("evaluationOuterId") Integer evaluationOuterId);
}
