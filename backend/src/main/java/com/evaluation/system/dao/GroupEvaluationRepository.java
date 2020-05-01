package com.evaluation.system.dao;

import com.evaluation.system.bean.EvaluationOuter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author acer
 */
public interface GroupEvaluationRepository extends JpaRepository<EvaluationOuter,Integer> {
    public EvaluationOuter findByEvaluationOuterId(int id);

    //通过班级号查找
    public List<EvaluationOuter> findAllByClassId(int id);
}
