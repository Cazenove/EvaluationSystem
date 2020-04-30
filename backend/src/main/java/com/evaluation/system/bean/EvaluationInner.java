package com.evaluation.system.bean;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Date;

//组内评价表类

@Entity
public class EvaluationInner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int evaluationInnerId;
    private String name;
    @Transient
    private int classId;
    private String content;//这个暂时先用string
    private Date releaseTime;//用的是util的date，不知道和sql的date有什么区别。出了问题再修改

    @ManyToOne
    @JoinColumn(name = "class_id")
    @Fetch(value= FetchMode.JOIN)
    private Class classInfo;

    public EvaluationInner(){

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
