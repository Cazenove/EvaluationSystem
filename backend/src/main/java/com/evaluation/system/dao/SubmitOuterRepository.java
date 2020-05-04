package com.evaluation.system.dao;

import com.evaluation.system.bean.GroupSuggestion;
import com.evaluation.system.bean.SubmitOuter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface SubmitOuterRepository extends JpaRepository<SubmitOuter,Integer> {
    public SubmitOuter findByEvaluationOuterIdAndGroupId(int evaluationId,int groupId);

    @Query("SELECT a FROM SubmitOuter a WHERE a.evaluationOuterId = :evaluationOuterId")
    public ArrayList<SubmitOuter> findByEvaluationOuterId(@Param("evaluationOuterId") Integer evaluationOuterId);
}
