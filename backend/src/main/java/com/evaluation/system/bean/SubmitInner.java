package com.evaluation.system.bean;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.Map;

//组内评价提交记录表
@Entity
public class SubmitInner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int submitInnerId;
    private int groupId;
    private int evaluationInnerId;
    @Type(type = "json")
    @Column(columnDefinition = "json")
    private Map<String,Object> content;
    private Date submitTime;

    public SubmitInner(){

    }

    @Override
    public String toString() {
        return "SubmitInner{" +
                "submitInnerId=" + submitInnerId +
                ", groupId=" + groupId +
                ", evaluationInnerId=" + evaluationInnerId +
                ", content='" + content + '\'' +
                ", submitTime=" + submitTime +
                '}';
    }

    public int getSubmitInnerId() {
        return submitInnerId;
    }

    public void setSubmitInnerId(int submitInnerId) {
        this.submitInnerId = submitInnerId;
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

    public Map<String, Object> getContent() {
        return content;
    }

    public void setContent(Map<String, Object> content) {
        this.content = content;
    }

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }
}