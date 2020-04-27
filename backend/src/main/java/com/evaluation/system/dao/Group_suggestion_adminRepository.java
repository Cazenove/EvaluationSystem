package com.evaluation.system.dao;

import com.evaluation.system.bean.Group_suggestion_admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface Group_suggestion_adminRepository extends JpaRepository<Group_suggestion_admin,Integer> {
    //通过被建议的组号查询
    public List<Group_suggestion_admin> findByGroupId(Integer group_id);

}
