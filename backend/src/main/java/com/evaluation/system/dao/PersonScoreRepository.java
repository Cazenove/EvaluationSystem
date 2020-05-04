package com.evaluation.system.dao;

import com.evaluation.system.bean.PersonScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author 221701230张增燊
 */
public interface PersonScoreRepository extends JpaRepository<PersonScore,Integer> {

    /**
     * @author 221701310_陈家祯
     */
    @Query("SELECT a FROM PersonScore a WHERE a.userId = :userId")
    public PersonScore findByUserId(String userId);
}
