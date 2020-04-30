package com.evaluation.system.bean;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//组件评价表类

@Entity
public class EvaluationOuter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int evaluationOuterId;
    private String name;
    private String content;//这个暂时先用string
    private Date releaseTime;//用的是util的date，不知道和sql的date有什么区别。出了问题再修改
    private Date endTime;

    @Transient
    private int classId;



    @ManyToOne
    @JoinColumn(name = "class_id")
    @Fetch(value= FetchMode.JOIN)
    private Class classInfo;

    public EvaluationOuter(){

    }

    @Override
    public String toString() {
        return "EvaluationOuter{" +
                "evaluationOuterId=" + evaluationOuterId +
                ", name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", releaseTime=" + releaseTime +
                ", classInfo=" + classInfo +
                '}';
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public int getEvaluationOuterId() {
        return evaluationOuterId;
    }

    public void setEvaluationOuterId(int evaluationOuterId) {
        this.evaluationOuterId = evaluationOuterId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class getClassInfo() {
        return classInfo;
    }

    public void setClassInfo(Class classInfo) {
        this.classInfo = classInfo;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(Date releaseTime) {
        this.releaseTime = releaseTime;
    }
}
