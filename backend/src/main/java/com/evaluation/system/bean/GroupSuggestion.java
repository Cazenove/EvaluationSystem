package com.evaluation.system.bean;

import com.vladmihalcea.hibernate.type.json.JsonStringType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.Map;

/**
 * 小组收到的建议表
 * @author 221701230张增燊
 */
@Entity
@Table(name = "group_suggestion")
@TypeDef(name = "json",typeClass = JsonStringType.class)
public class GroupSuggestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_suggestion_id")
    private int groupSuggestionId;
    @Column(name = "group_id")
    private int groupId;
    @Column(name = "evaluation_outer_id")
    private int evaluationOuterId;

    @Column(name = "content")
    private String suggestion;

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

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }
}
