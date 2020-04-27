package com.evaluation.system.bean;

import javax.persistence.*;

@Entity
public class Group_suggestion_admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int group_suggestion_admin_id;
    private String admin_id;

    @Column(name = "group_id")
    private int groupId;
    private String content;//暂时先用string代替jaon

    public Group_suggestion_admin(){

    }

    public int getGroup_suggestion_admin_id() {
        return group_suggestion_admin_id;
    }

    public void setGroup_suggestion_admin_id(int group_suggestion_admin_id) {
        this.group_suggestion_admin_id = group_suggestion_admin_id;
    }

    public String getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(String admin_id) {
        this.admin_id = admin_id;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
