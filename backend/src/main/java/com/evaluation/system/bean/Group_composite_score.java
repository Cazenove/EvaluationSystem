package com.evaluation.system.bean;

//小组综合得分表类

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Group_composite_score {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int group_composite_score_id;
    private int group_id;
    private String content;//先用String吧，我也不知道json是怎样的

    public Group_composite_score(){

    }

    public int getGroup_composite_score_id() {
        return group_composite_score_id;
    }

    public void setGroup_composite_score_id(int group_composite_score_id) {
        this.group_composite_score_id = group_composite_score_id;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
