package com.evaluation.system.bean;

import javax.persistence.*;
import java.util.Date;

//组间评价表类

@Entity
public class Evaluation_outer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "evaluation_outer_id")
    private int evaluationOuterId;
    private String name;
    private int class_id;
    private String content;//这个暂时先用string
    private String release_time;//用的是util的date，不知道和sql的date有什么区别。出了问题再修改

    public Evaluation_outer(){

    }

    public int getEvaluation_outer_id() {
        return evaluationOuterId;
    }

    public void setEvaluation_outer_id(int evaluationOuterId) {
        this.evaluationOuterId = evaluationOuterId;
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

    public String getRelease_time() {
        return release_time;
    }

    public void setRelease_time(String release_time) {
        this.release_time = release_time;
    }
}
