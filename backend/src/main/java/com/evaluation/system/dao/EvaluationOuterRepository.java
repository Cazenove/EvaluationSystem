package com.evaluation.system.dao;

import com.evaluation.system.bean.Admin;
import com.evaluation.system.bean.EvaluationOuter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvaluationOuterRepository extends JpaRepository<EvaluationOuter,Integer> {
}
