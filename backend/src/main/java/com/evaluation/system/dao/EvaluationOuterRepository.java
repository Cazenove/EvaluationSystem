package com.evaluation.system.dao;

import com.evaluation.system.bean.EvaluationInner;
import com.evaluation.system.bean.EvaluationOuter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author acer
 */
public interface EvaluationOuterRepository extends JpaRepository<EvaluationOuter,Integer> {


    @Query("SELECT a FROM EvaluationOuter a WHERE a.classInfo.classId = :classId and a.evaluationOuterId = :evaluationOuterId")
    EvaluationOuter findAllByClassIdAndEvaluationOuterId(@Param("classId")int classId,
                                                               @Param("evaluationOuterId")int evaluationOuterId);
    @Query("SELECT a FROM EvaluationOuter a WHERE a.classInfo.classId = :classId")
    List<EvaluationOuter> findAllByClassId(@Param("classId")int classId);
    /**
     * @author 221701310_陈家祯
     */
    @Query("SELECT a FROM EvaluationOuter a WHERE a.evaluationOuterId = :evaluationOuterId")
    public EvaluationOuter findOneByEvaluationOuterId(@Param("evaluationOuterId")int evaluationOuterId);
}
