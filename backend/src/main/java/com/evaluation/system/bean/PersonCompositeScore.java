package com.evaluation.system.bean;

import com.vladmihalcea.hibernate.type.json.JsonStringType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.Map;

/**
 * 个人综合得分表类
 * @author 221701230张增燊
 */
@Entity
@Table(name = "person_composite_score")
@TypeDef(name = "json",typeClass = JsonStringType.class)
public class PersonCompositeScore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_composite_score_id")
    private int personCompositeScoreId;
    @Column(name = "group_id")
    private int groupId;
    @Column(name = "user_id")
    private String userId;

    @Column(name = "content")
    private float score;

    @Transient
    private String className;
    @Transient
    private String groupName;
    @Transient
    private String userName;
    @Transient
    private int classId;

    public PersonCompositeScore(){

    }

    public int getPersonCompositeScoreId() {
        return personCompositeScoreId;
    }

    public void setPersonCompositeScoreId(int personCompositeScoreId) {
        this.personCompositeScoreId = personCompositeScoreId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

}
