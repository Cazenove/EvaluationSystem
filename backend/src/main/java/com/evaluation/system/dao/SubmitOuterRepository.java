package com.evaluation.system.dao;

import com.evaluation.system.bean.EvaluationInner;
import com.evaluation.system.bean.SubmitOuter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubmitOuterRepository extends JpaRepository<SubmitOuter,Integer> {
}
