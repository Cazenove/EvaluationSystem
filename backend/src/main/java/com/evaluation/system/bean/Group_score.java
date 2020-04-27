package com.evaluation.system.bean;

//小组历次得分表

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Group_score {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int group_score_id;
    private int group_id;
    private int evaluation_outer_id;
    private String content;//先用String代替jason

    public Group_score(){

    }

    public int getGroup_score_id() {
        return group_score_id;
    }

    public void setGroup_score_id(int group_score_id) {
        this.group_score_id = group_score_id;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public int getEvaluation_outer_id() {
        return evaluation_outer_id;
    }

    public void setEvaluation_outer_id(int evaluation_outer_id) {
        this.evaluation_outer_id = evaluation_outer_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
