package com.evaluation.system.bean;

//个人历次贡献率及分工情况表类

import org.springframework.scheduling.support.SimpleTriggerContext;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Person_score {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int person_score_id;
    private int group_id;
    private int evaluation_inner_id;
    private String user_id;
    private String content;//暂时先用String代替json吧

    public Person_score(){

    }

    public int getPerson_score_id() {
        return person_score_id;
    }

    public void setPerson_score_id(int person_score_id) {
        this.person_score_id = person_score_id;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public int getEvaluation_inner_id() {
        return evaluation_inner_id;
    }

    public void setEvaluation_inner_id(int evaluation_inner_id) {
        this.evaluation_inner_id = evaluation_inner_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
