package com.smartin.timedic.caregiver.model.parammodel;

import android.text.TextUtils;
import android.util.Patterns;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Hafid on 1/1/2018.
 */

public class ScheduleParam {

    @SerializedName("day")
    private String day;

    @SerializedName("startTime")
    private String startTime;

    @SerializedName("startTime2")
    private String startTime2;

    @SerializedName("endTime")
    private String endTime;

    @SerializedName("endTime2")
    private String endTime2;

    @SerializedName("idHomecareCaregiver")
    private Long idHomecareCaregiver;

    @SerializedName("status")
    private Boolean status;


    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStartTime2() {
        return startTime2;
    }

    public void setStartTime2(String startTime2) {
        this.startTime2 = startTime2;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getEndTime2() {
        return endTime2;
    }

    public void setEndTime2(String endTime2) {
        this.endTime2 = endTime2;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Long getIdHomecareCaregiver() {
        return idHomecareCaregiver;
    }

    public void setIdHomecareCaregiver(Long idHomecareCaregiver) {
        this.idHomecareCaregiver = idHomecareCaregiver;
    }

    @Override
    public String toString() {
        return "ScheduleParam{" +
                "day='" + day + '\'' +
                ", startTime='" + startTime + '\'' +
                ", startTime2='" + startTime2 + '\'' +
                ", endTime='" + endTime + '\'' +
                ", endTime2='" + endTime2 + '\'' +
                ", idHomecareCaregiver=" + idHomecareCaregiver +
                ", status=" + status +
                '}';
    }
}
