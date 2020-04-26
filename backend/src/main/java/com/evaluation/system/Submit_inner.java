package com.evaluation.system;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

//组内评价提交记录表
@Entity
public class Submit_inner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int submit_inner_id;
    private int group_id;
    private int evaluation_inner_id;
    private String content;//暂时先用String代替json吧
    private Date submit_time;//这是util的date，不是sql的date，不知道会不会出问题

    public Submit_inner(){

    }

    public int getSubmit_inner_id() {
        return submit_inner_id;
    }

    public void setSubmit_inner_id(int submit_inner_id) {
        this.submit_inner_id = submit_inner_id;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getSubmit_time() {
        return submit_time;
    }

    public void setSubmit_time(Date submit_time) {
        this.submit_time = submit_time;
    }
}