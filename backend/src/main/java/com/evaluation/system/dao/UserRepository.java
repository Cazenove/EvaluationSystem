package com.evaluation.system.dao;

import com.evaluation.system.bean.EvaluationInner;
import com.evaluation.system.bean.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.Column;
import java.util.ArrayList;
import java.util.List;

public interface UserRepository extends JpaRepository<User,String> {
    //通过组号查询
    public List<User> findByGroupId(int groupId);
    //通过学号查询
    public User findByUserId(String userId);

    /**
     * 通过groupId、status来获取组长（一人）
     * @param groupId 小组id
     * @param status 状态
     * @return User
     * @author 221701310_陈家祯
     */
    @Query("SELECT a FROM User a WHERE a.groupId = :groupId and a.status =:status")
    public User findOneByGroupIdAndAndStatus(@Param("groupId") Integer groupId,@Param("status") String status);

    /**
     * 通过groupId、status来获取组员（多人）
     * @param groupId 小组id
     * @param status 状态
     * @return ArrayList<User>
     * @author 221701310_陈家祯
     */
    @Query("SELECT a FROM User a WHERE a.groupId = :groupId and a.status =:status")
    public ArrayList<User> findByGroupIdAndStatus(@Param("groupId") Integer groupId,@Param("status") String status);

    /**
     * 通过userId来获取用户
     * @param userId 小组id
     * @return User
     * @author 221701310_陈家祯
     */
    @Query("SELECT a FROM User a WHERE a.userId = :userId")
    public User findOneByUserId(String userId);

    //通过手机号查询
    @Query("select u from User as u where u.telephone = :telephone")
    User findByTelephone(@Param("telephone") String telephone);
}