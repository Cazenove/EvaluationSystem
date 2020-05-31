package com.evaluation.system.bean;

import com.vladmihalcea.hibernate.type.json.JsonStringType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.Map;

/**
 * 个人历次贡献率及分工情况表类
 * @author 10651
 */
@Entity
@Table(name = "person_score")
@TypeDef(name = "json",typeClass = JsonStringType.class)
public class PersonScore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_score_id")
    private int personScoreId;
    @Column(name = "group_id")
    private int groupId;
    @Column(name = "evaluation_inner_id")
    private int evaluationInnerId;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "decision")
    private String decision;

    private int content;

//    @Transient
//    private String className;
//    @Transient
//    private String groupName;
//    @Transient
//    private String userName;
//    @Transient
//    private int classId;

    public PersonScore(){

    }

    public int getPersonScoreId() {
        return personScoreId;
    }

    public void setPersonScoreId(int personScoreId) {
        this.personScoreId = personScoreId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getEvaluationInnerId() {
        return evaluationInnerId;
    }

    public void setEvaluationInnerId(int evaluationInnerId) {
        this.evaluationInnerId = evaluationInnerId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getContent() {
        return content;
    }

    public void setContent(int content) {
        this.content = content;
    }

//    public String getClassName() {
//        return className;
//    }
//
//    public void setClassName(String className) {
//        this.className = className;
//    }
//
//    public String getGroupName() {
//        return groupName;
//    }
//
//    public void setGroupName(String groupName) {
//        this.groupName = groupName;
//    }
//
//    public String getUserName() {
//        return userName;
//    }
//
//    public void setUserName(String userName) {
//        this.userName = userName;
//    }
//
//    public int getClassId() {
//        return classId;
//    }
//
//    public void setClassId(int classId) {
//        this.classId = classId;
//    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }
}
