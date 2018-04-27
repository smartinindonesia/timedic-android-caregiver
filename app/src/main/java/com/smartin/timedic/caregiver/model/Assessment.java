package com.smartin.timedic.caregiver.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hafid on 9/24/2017.
 */

public class Assessment implements Serializable {

    @SerializedName("id")
    private int id;
    @SerializedName("assessmentAnswer")
    private String assessmentAnswer;
    @SerializedName("filePath")
    private String filePath;
    @SerializedName("idAssessment")
    private IDAssessment idAssessment;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAssessmentAnswer() {
        return assessmentAnswer;
    }

    public void setAssessmentAnswer(String assessmentAnswer) {
        this.assessmentAnswer = assessmentAnswer;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public IDAssessment getIdAssessment() {
        return idAssessment;
    }

    public void setIdAssessment(IDAssessment idAssessment) {
        this.idAssessment = idAssessment;
    }

}
