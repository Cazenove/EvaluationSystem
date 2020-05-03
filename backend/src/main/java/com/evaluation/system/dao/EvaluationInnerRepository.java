package com.evaluation.system.dao;

import com.evaluation.system.bean.EvaluationInner;
import com.evaluation.system.bean.EvaluationOuter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 221701310_陈家祯
 */
@Repository
public interface EvaluationInnerRepository extends JpaRepository<EvaluationInner,Integer> {
    /**通过班级id找到组内评分表
     * @param classId 班级id
     * @return List<EvaluationInner>
     **/
    @Query("SELECT a FROM EvaluationInner a WHERE a.classInfo.classId = :classId")
    List<EvaluationInner> findByClassId(@Param("classId") int classId);

    /**通过组内评分表id找到组内评分表
     * @param evaluationInnerId 组内评分表id
     * @return EvaluationInner
     **/
    //EvaluationInner findEvaluationInnerByEvaluationInnerId(Integer id);
}
