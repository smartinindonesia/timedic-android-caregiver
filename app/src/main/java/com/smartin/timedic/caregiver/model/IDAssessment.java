package com.smartin.timedic.caregiver.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Hafid on 27/04/2018.
 */

public class IDAssessment implements Serializable {
    @SerializedName("id")
    private Long id;
    @SerializedName("question")
    private String question;
    @SerializedName("statusActive")
    private Integer statusActive;
    @SerializedName("assessmentType")
    private Integer assessmentType;
    @SerializedName("rootId")
    private Long rootId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Integer getStatusActive() {
        return statusActive;
    }

    public void setStatusActive(Integer statusActive) {
        this.statusActive = statusActive;
    }

    public Integer getAssessmentType() {
        return assessmentType;
    }

    public void setAssessmentType(Integer assessmentType) {
        this.assessmentType = assessmentType;
    }

    public Long getRootId() {
        return rootId;
    }

    public void setRootId(Long rootId) {
        this.rootId = rootId;
    }
}