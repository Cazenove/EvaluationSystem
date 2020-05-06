package com.evaluation.system.dao;

import com.evaluation.system.bean.SubmitInner;
import com.evaluation.system.bean.SubmitOuter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface SubmitInnerRepository extends JpaRepository<SubmitInner,Integer> {
    @Query("SELECT a FROM SubmitInner a WHERE a.evaluationInnerId = :evaluationInnerId")
    public ArrayList<SubmitInner> findByEvaluationInnerId(@Param("evaluationInnerId") Integer evaluationInnerId);
}
