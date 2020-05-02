package com.evaluation.system.bean;

//小组收到的建议表

import com.vladmihalcea.hibernate.type.json.JsonStringType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.Map;

@Entity(name = "Group_suggestion")
public class GroupSuggestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int groupSuggestionId;
    private int groupId;
    private int evaluationOuterId;

    private String content;

    public GroupSuggestion(){

    }

    public int getGroupSuggestionId() {
        return groupSuggestionId;
    }

    public void setGroupSuggestionId(int groupSuggestionId) {
        this.groupSuggestionId = groupSuggestionId;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}