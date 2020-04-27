package com.evaluation.system.dao;

import com.evaluation.system.bean.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin,Integer> {
}
