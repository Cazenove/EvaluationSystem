package com.evaluation.system.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//组件评价表类

/**
 * @author 221701128
 */
@Entity
@TypeDef(name = "json",typeClass = JsonStringType.class)
public class EvaluationOuter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int evaluationOuterId;
    private String name;
    private Date releaseTime;
    private Date endTime;
    @Type(type = "json")
    @Column(columnDefinition = "json")
    private Map<String,Object> content;

    @Transient
    private int classId;
    //瞬时字段 只是用来接收classId参数的作用，其他基本用不到



    @ManyToOne
    @JoinColumn(name = "class_id")
    @Fetch(value= FetchMode.JOIN)
    private Class classInfo;
    //通过外键得到的class的信息

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

    public Map<String, Object> getContent() {
        return content;
    }

    public void setContent(Map<String, Object> content) {
        this.content = content;
    }

    public Date getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(Date releaseTime) {
        this.releaseTime = releaseTime;
    }
}
