package com.evaluation.system.dao;

import com.evaluation.system.bean.EvaluationInner;
import com.evaluation.system.bean.EvaluationOuter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvaluationInnerRepository extends JpaRepository<EvaluationInner,Integer> {
}
