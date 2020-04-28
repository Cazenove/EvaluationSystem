package com.evaluation.system;

import com.evaluation.system.bean.Class;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassRepository extends JpaRepository<Class,Integer> {
    /*
    班级ID查询
     */
    @Query("select c from Class as c where c.class_id = :class_id")
    Class findByClassId(@Param("class_id") Integer class_id);

    /*
    查找全部
     */
    @Query("select c from Class as c")
    List<Class> findAll();
}
