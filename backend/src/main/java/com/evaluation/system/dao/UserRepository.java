package com.evaluation.system.dao;

import com.evaluation.system.bean.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.Column;
import java.util.List;

public interface UserRepository extends JpaRepository<User,String> {
    //通过组号查询
    public List<User> findByGroupId(int groupId);
    //通过学号查询
    public User findByUserId(String userId);
    //通过手机号查询
    public  User findByTelephone(String telephone);


}