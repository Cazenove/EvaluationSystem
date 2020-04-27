package com.evaluation.system.bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

//组间评价提交记录表类
@Entity
public class Submit_outer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int submit_outer_id;
    private int group_id;
    private int evaluation_outer_id;
    private String content;
    private Date submit_time;

    public Submit_outer(){

    }

    public int getSubmit_outer_id() {
        return submit_outer_id;
    }

    public void setSubmit_outer_id(int submit_outer_id) {
        this.submit_outer_id = submit_outer_id;
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

    public Date getSubmit_time() {
        return submit_time;
    }

    public void setSubmit_time(Date submit_time) {
        this.submit_time = submit_time;
    }
}
