package com.smartin.timedic.caregiver.model.responsemodel;

import com.google.gson.annotations.SerializedName;

import com.smartin.timedic.caregiver.model.Assessment;

/**
 * Created by Hafid on 1/11/2018.
 */

public class AssessmentResponse {
    @SerializedName("id")
    private Long id;
    @SerializedName("idAssessment")
    private Assessment assessment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Assessment getAssessment() {
        return assessment;
    }

    public void setAssessment(Assessment assessment) {
        this.assessment = assessment;
    }
}
