package com.evaluation.system.bean;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

//班级类

/**
 * @author 221701128 chh
 */
@Entity
public class Class {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int classId;
    @Column(name = "name")
    private String className;
    private int groupNum;
    private Date startTime;
    private Date endTime;
    private String status;

    @OneToMany(mappedBy = "classInfo")
    private Set<EvaluationOuter> list = new HashSet<EvaluationOuter>();

    public Class(){

    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassName() {
        return className;
    }

    public int getGroupNum() {
        return groupNum;
    }

    public void setGroupNum(int groupNum) {
        this.groupNum = groupNum;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Class{" +
                "classId=" + classId +
                ", name='" + className + '\'' +
                ", groupNum=" + groupNum +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", status='" + status + '\'' +
                '}';
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
