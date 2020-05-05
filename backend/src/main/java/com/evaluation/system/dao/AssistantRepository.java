package com.evaluation.system.dao;

import com.evaluation.system.bean.Assistant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AssistantRepository extends JpaRepository<Assistant,String> {
    public Assistant findByAssistantId(String id);
    //助教id查询
    @Query("select a from Assistant as a where a.assistant_id = :assistant_id")
    Assistant findByAssistantId(@Param("assistant_id") String assistant_id);
    //助教电话查询
    @Query("select a from Assistant as a where a.telephone = :telephone")
    Assistant findByTelephone(@Param("telephone") String telephone);
}
