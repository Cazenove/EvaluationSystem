package com.evaluation.system.bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

//组内评价提交记录表
@Entity
public class SubmitInner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int submitInnerId;
    private int groupId;
    private int evaluationInnerId;
    private String content;//暂时先用String代替json吧
    private Date submitTime;//这是util的date，不是sql的date，不知道会不会出问题

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