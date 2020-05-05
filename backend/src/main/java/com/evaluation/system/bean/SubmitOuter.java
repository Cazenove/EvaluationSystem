package com.evaluation.system.bean;

import com.vladmihalcea.hibernate.type.json.JsonStringType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.Date;
import java.util.Map;

//组间评价提交记录表类
@Entity
@TypeDef(name = "json",typeClass = JsonStringType.class)
public class SubmitOuter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int submitOuterId;
    private int groupId;
    private int evaluationOuterId;
    @Type(type = "json")
    @Column(columnDefinition = "json")
    private Map<String,Object> content;
    private String submitTime;

    private int classId;

    public SubmitOuter(){

    }

    @Override
    public String toString() {
        return "SubmitOuter{" +
                "submitOuterId=" + submitOuterId +
                ", groupId=" + groupId +
                ", evaluationOuterId=" + evaluationOuterId +
                ", content='" + content + '\'' +
                '}';
    }

    public String toSuggestion(){
        return ""+ content.get("suggestion") ;
    }

    public int getSubmitOuterId() {
        return submitOuterId;
    }

    public void setSubmitOuterId(int submitOuterId) {
        this.submitOuterId = submitOuterId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getEvaluationOuterId() {
        return evaluationOuterId;
    }

    public void setEvaluationOuterId(int evaluationOuterId) {
        this.evaluationOuterId = evaluationOuterId;
    }

    public Map<String, Object> getContent() {
        return content;
    }

    public void setContent(Map<String, Object> content) {
        this.content = content;
    }

    public String getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(String submitTime) {
        this.submitTime = submitTime;
    }

    public void setClassId(int classId){this.classId = classId;}

    public int getClassId(){return classId;}
}