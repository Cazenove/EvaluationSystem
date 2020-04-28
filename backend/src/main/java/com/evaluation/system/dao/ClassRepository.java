package com.evaluation.system.dao;

import com.evaluation.system.bean.Class;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ClassRepository extends JpaRepository<Class,Integer> {
    /*
    班级ID查询
     */
    @Query("select c from Class as c where c.classId = :classId")
    Class findByClassId(@Param("classId") Integer classId);

}
