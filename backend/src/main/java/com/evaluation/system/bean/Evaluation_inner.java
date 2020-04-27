package com.evaluation.system.bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

//组内评价表类

@Entity
public class Evaluation_inner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int evaluation_inner_id;
    private String name;
    private int class_id;
    private String content;//这个暂时先用string
    private Date release_time;//用的是util的date，不知道和sql的date有什么区别。出了问题再修改

    public Evaluation_inner(){

    }

    public int getEvaluation_inner_id() {
        return evaluation_inner_id;
    }

    public void setEvaluation_inner_id(int evaluation_inner_id) {
        this.evaluation_inner_id = evaluation_inner_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getRelease_time() {
        return release_time;
    }

    public void setRelease_time(Date release_time) {
        this.release_time = release_time;
    }
}
