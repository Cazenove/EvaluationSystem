package com.evaluation.system;

//小组收到的建议表

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Group_suggestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int group_suggestion_id;
    private int group_id;
    private int evaluation_outer_id;
    private String content;//暂时先用String代替json吧

    public Group_suggestion(){

    }

    public int getGroup_suggestion_id() {
        return group_suggestion_id;
    }

    public void setGroup_suggestion_id(int group_suggestion_id) {
        this.group_suggestion_id = group_suggestion_id;
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
