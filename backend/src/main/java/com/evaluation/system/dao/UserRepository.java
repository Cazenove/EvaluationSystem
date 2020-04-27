package com.evaluation.system.dao;

import com.evaluation.system.bean.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Integer> {
    //通过组号查询
    public List<User> findByGroupId(int group_id);
}
