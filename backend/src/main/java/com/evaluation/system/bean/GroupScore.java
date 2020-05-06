package com.evaluation.system.bean;

import com.vladmihalcea.hibernate.type.json.JsonStringType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.Map;

/**
 * 小组历次得分表
 * @author 221701230张增燊
 */
@Entity
@Table(name = "group_score")
@TypeDef(name = "json",typeClass = JsonStringType.class)
public class GroupScore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_score_id")
    private int groupScoreId;
    @Column(name = "group_id")
    private int groupId;
    @Column(name = "evaluation_outer_id")
    private int evaluationOuterId;
    @Column(name = "content")
    private int content;

    @Transient
    private int classId;
    @Transient
    private int groupNum;
    @Transient
    private String groupName;

    public GroupScore(){

    }

    public int getGroupScoreId() {
        return groupScoreId;
    }

    public void setGroupScoreId(int groupScoreId) {
        this.groupScoreId = groupScoreId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getEvaluationOuterId() {
        return evaluationOuterId;
    }

    public void setEvaluationOuterId(int evaluationOuterId) {
        this.evaluationOuterId = evaluationOuterId;
    }

    public int getContent() {
        return content;
    }

    public void setContent(int content) {
        this.content = content;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public int getGroupNum() {
        return groupNum;
    }

    public void setGroupNum(int groupNum) {
        this.groupNum = groupNum;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
