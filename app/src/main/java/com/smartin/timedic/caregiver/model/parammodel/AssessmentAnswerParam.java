package com.smartin.timedic.caregiver.model.parammodel;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Hafid on 2/18/2018.
 */

public class AssessmentAnswerParam {

    @SerializedName("idAssessment")
    private IdAssessment idAssessment;
    @SerializedName("assessmentAnswer")
    private String assessmentAnswer;
    @SerializedName("filePath")
    private String filePath;
    private double price;

    public AssessmentAnswerParam(IdAssessment idAssessment, String assessmentAnswer, String filePath) {
        this.idAssessment = idAssessment;
        this.assessmentAnswer = assessmentAnswer;
        this.filePath = filePath;
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

    public IdAssessment getIdAssessment() {
        return idAssessment;
    }

    public void setIdAssessment(IdAssessment idAssessment) {
        this.idAssessment = idAssessment;
    }

    public static class IdAssessment {
        @SerializedName("id")
        private Long id;

        public IdAssessment(Long id) {
            this.id = id;
        }

        public IdAssessment() {
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
