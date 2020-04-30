package com.evaluation.system.bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

//组间评价提交记录表类
@Entity
public class SubmitOuter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int submitOuterId;
    private int groupId;
    private int evaluationOuterId;
    private String content;
    private Date submitTime;

    public SubmitOuter(){

    }

    @Override
    public String toString() {
        return "SubmitOuter{" +
                "submitOuterId=" + submitOuterId +
                ", groupId=" + groupId +
                ", evaluationOuterId=" + evaluationOuterId +
                ", content='" + content + '\'' +
                ", submitTime=" + submitTime +
                '}';
    }

    public int getSubmitOuterId() {
        return submitOuterId;
    }

    public void setSubmitOuterId(int submitOuterId) {
        this.submitOuterId = submitOuterId;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }
}
