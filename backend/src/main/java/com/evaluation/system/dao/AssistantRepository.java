package com.evaluation.system.dao;

import com.evaluation.system.bean.Assistant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssistantRepository extends JpaRepository<Assistant,String> {
    public Assistant findByAssistantId(String id);
}
