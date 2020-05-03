package com.evaluation.system.bean;

import javax.persistence.*;

//管理员类

@Entity
public class Admin{
    @Id
    private String adminId;

    private String password;

    public Admin(){

    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
