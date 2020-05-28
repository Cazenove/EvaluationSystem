package com.evaluation.system.dao;

import com.evaluation.system.bean.PersonCompositeScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author 221701230张增燊
 */
public interface PersonCompositeScoreRepository extends JpaRepository<PersonCompositeScore,Integer> {
     /**通过学号找到个人综合得分
     * @author 221701230张增燊
     * @param userId 学号
     * @return PersonCompositeScore
     */
     @Query("SELECT a FROM PersonCompositeScore a WHERE a.userId = :userId")
     public PersonCompositeScore findByUserId(@Param("userId") String userId);
}
