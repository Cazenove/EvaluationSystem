package com.evaluation.system.dao;

import com.evaluation.system.bean.Evaluation_outer;
import com.evaluation.system.bean.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupEvaluation extends JpaRepository<Evaluation_outer,Integer> {
    public Evaluation_outer findByEvaluationOuterId(int id);
}
