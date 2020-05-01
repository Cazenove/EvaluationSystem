package com.evaluation.system.bean;

import com.vladmihalcea.hibernate.type.json.JsonStringType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.Map;

//组件评价表类

@Entity
@TypeDef(name = "json",typeClass = JsonStringType.class)
public class EvaluationOuter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int evaluationOuterId;
    private String name;
    private String releaseTime;
    private String endTime;
    @Type(type = "json")
    @Column(columnDefinition = "json")
    private Map<String,Object> content;

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

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
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

    public String getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(String releaseTime) {
        this.releaseTime = releaseTime;
    }
}