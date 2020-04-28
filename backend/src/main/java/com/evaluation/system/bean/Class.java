package com.evaluation.system.bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

//班级类

@Entity
public class Class {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int class_id;
    private String name;
    private int group_num;
    private Date start_time;//用的是util的date，不知道和sql的date有什么区别。出了问题再修改
    private Date end_time;//用的是util的date，不知道和sql的date有什么区别。出了问题再修改
    private String status;

    public Class(){

    }

    @Override
    public String toString() {
        return "Class{" +
                "class_id=" + class_id +
                ", name='" + name + '\'' +
                ", group_num=" + group_num +
                ", start_time=" + start_time +
                ", end_time=" + end_time +
                ", status='" + status + '\'' +
                '}';
    }

    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGroup_num() {
        return group_num;
    }

    public void setGroup_num(int group_num) {
        this.group_num = group_num;
    }

    public Date getStart_time() {
        return start_time;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
