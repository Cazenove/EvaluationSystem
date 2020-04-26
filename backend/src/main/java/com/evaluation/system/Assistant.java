package com.evaluation.system;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

//助教类

@Entity
public class Assistant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String assistant_id;
    private String password;
    private String name;
    private int class_id;
    private String telephone;

    public Assistant(){

    }

    public String getAssistant_id() {
        return assistant_id;
    }

    public void setAssistant_id(String assistant_id) {
        this.assistant_id = assistant_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    ublic String getName() {
        return name;
    }

    public void setName(String assistant_id) {
        this.name = name;
    }

    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
