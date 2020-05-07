package com.evaluation.system.dao;

import com.evaluation.system.bean.GroupScore;
import com.evaluation.system.bean.PersonScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author 221701230张增燊
 */
public interface PersonScoreRepository extends JpaRepository<PersonScore,Integer> {

    /**
     * 通过userId来获取个人分工和贡献
     * @param userId 用户id
     * @return PersonScore
     * @author 221701310_陈家祯
     */
    @Query("SELECT a FROM PersonScore a WHERE a.userId = :userId")
    public PersonScore findByUserId(String userId);

    /**
     *
     * @param evaluationInnerId
     * @param userId
     * @return PersonScore
     */
    @Query("SELECT a FROM PersonScore a WHERE a.evaluationInnerId = :evaluationInnerId and a.userId = :userId")
    public PersonScore findOneByIdAndUserId(@Param("evaluationInnerId") int evaluationInnerId , @Param("userId") String userId);
}
