package com.evaluation.system.bean;

import java.util.List;

/**
 * @author 221701230张增燊
 * 用于返回历次贡献率的工具类
 */
public class PersonScoreHelper {
    private String className;

    private String groupName;

    private String userName;

    private int classId;

    private String telephone;

    private String userId;

    List<PersonScore> personScores;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public int getClassId() {
        return classId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupName() {
        return groupName;
    }

    public List<PersonScore> getPersonScores() {
        return personScores;
    }

    public void setPersonScores(List<PersonScore> personScores) {
        this.personScores = personScores;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
