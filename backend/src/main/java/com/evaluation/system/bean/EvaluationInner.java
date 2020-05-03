package com.evaluation.system.bean;

import com.vladmihalcea.hibernate.type.json.JsonStringType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.Date;
import java.util.Map;

//组内评价表类

/**
 * @author 221701128 chh
 */
@Entity
@TypeDef(name = "json",typeClass = JsonStringType.class)
public class EvaluationInner {
    @Id
    private int evaluationInnerId;
    private String name;
    @Transient
    private int classId;
    @Type(type = "json")
    private Map<String,Object> content;
    private Date releaseTime;
    private Date endTime;

    @ManyToOne
    @JoinColumn(name = "class_id")
    @Fetch(value= FetchMode.JOIN)
    private Class classInfo;

    public EvaluationInner(){

    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Class getClassInfo() {
        return classInfo;
    }

    public void setClassInfo(Class classInfo) {
        this.classInfo = classInfo;
    }

    public int getEvaluationInnerId() {
        return evaluationInnerId;
    }

    public void setEvaluationInnerId(int evaluationInnerId) {
        this.evaluationInnerId = evaluationInnerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
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
