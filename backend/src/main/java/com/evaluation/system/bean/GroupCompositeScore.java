package com.evaluation.system.bean;



import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.Map;

/**
 * 小组综合得分表类
 * @author 221701230张增燊
 */
@Entity
@Table(name = "group_composite_score")
@TypeDef(name = "json",typeClass = JsonStringType.class)
public class GroupCompositeScore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_composite_score_id")
    private int groupCompositeScoreId;
    @Column(name = "group_id")
    private int groupId;

    @Column(name = "content")
    private float score;

    @Transient
    private int classId;
    @Transient
    private int groupNum;
    @Transient
    private String groupName;

    public GroupCompositeScore(){

    }

    public int getGroupCompositeScoreId() {
        return groupCompositeScoreId;
    }

    public void setGroupCompositeScoreId(int groupCompositeScoreId) {
        this.groupCompositeScoreId = groupCompositeScoreId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getGroupNum() {
        return groupNum;
    }

    public void setGroupNum(int groupNum) {
        this.groupNum = groupNum;
    }
}
