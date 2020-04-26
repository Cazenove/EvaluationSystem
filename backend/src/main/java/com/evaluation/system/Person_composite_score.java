package com.evaluation.system;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

//个人综合得分表

@Entity
public class Person_composite_score {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int person_composite_score_id;
    private int group_id;
    private String user_id;
    private String content;//暂时先用String代替json吧

    public Person_composite_score(){

    }

    public int getPerson_composite_score_id() {
        return person_composite_score_id;
    }

    public void setPerson_composite_score_id(int person_composite_score_id) {
        this.person_composite_score_id = person_composite_score_id;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
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
