package com.smartin.timedic.caregiver.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hafid on 9/24/2017.
 */

public class Assessment implements Serializable{

    @SerializedName("id")
    private int id;
    @SerializedName("question")
    private String questions;
    @SerializedName("rootId")
    private int root_id;
    @SerializedName("statusActive")
    private int statusActive;
    @SerializedName("homecareAssessmentOptionCollection")
    private List<AssessmentOption> options = new ArrayList<>();
    private int id_multiple_choice;
    private String answer;
    private String path_file;
    @SerializedName("assessmentType")
    private int assestmentType;

    private double selectedPrice;

    public Assessment(){

    }

    public int getStatusActive() {
        return statusActive;
    }

    public void setStatusActive(int statusActive) {
        this.statusActive = statusActive;
    }

    public List<AssessmentOption> getOptions() {
        return options;
    }

    public void setOptions(List<AssessmentOption> options) {
        this.options = options;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestions() {
        return questions;
    }

    public void setQuestions(String questions) {
        this.questions = questions;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getRoot_id() {
        return root_id;
    }

    public void setRoot_id(int root_id) {
        this.root_id = root_id;
    }

    public int getId_multiple_choice() {
        return id_multiple_choice;
    }

    public void setId_multiple_choice(int id_multiple_choice) {
        this.id_multiple_choice = id_multiple_choice;
    }

    public String getPath_file() {
        return path_file;
    }

    public void setPath_file(String path_file) {
        this.path_file = path_file;
    }

    public void clearOptionAssestment(){
        this.options.clear();
    }

    public int getAssestmentType() {
        return assestmentType;
    }

    public void setAssestmentType(int assestmentType) {
        this.assestmentType = assestmentType;
    }

    public static class AssessmentType {
        @SerializedName("id")
        private int id;
        @SerializedName("type")
        private String type;

        public AssessmentType(){

        }

        public AssessmentType(int id, String type) {
            this.id = id;
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public double getSelectedPrice() {
        return selectedPrice;
    }

    public void setSelectedPrice(double selectedPrice) {
        this.selectedPrice = selectedPrice;
    }
}
