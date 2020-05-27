package com.evaluation.system.dao;

import com.evaluation.system.bean.PersonCompositeScore;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 221701230张增燊
 */
public interface PersonCompositeScoreRepository extends JpaRepository<PersonCompositeScore,Integer> {
     /**通过学号找到个人综合得分
     * @author 221701230张增燊
     * @param userId 学号
     * @return PersonCompositeScore
     */
     public PersonCompositeScore findByUserId(String userId);
}
