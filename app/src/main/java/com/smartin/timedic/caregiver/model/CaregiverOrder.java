package com.smartin.timedic.caregiver.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Hafid on 3/26/2018.
 */

public class CaregiverOrder implements Serializable{

    @SerializedName("id")
    private Long id;

    @SerializedName("caregiverName")
    private String caregiverName;

    @SerializedName("reasonAcceptanceStatus")
    private String reasonAcceptanceStatus;

    @SerializedName("registerNurseNumber")
    private String registerNurseNumber;

    @SerializedName("idCaregiver")
    private Long caregiverId;

    @SerializedName("acceptanceStatus")
    private Boolean acceptanceStatus;

    @SerializedName("rateStatus")
    private Boolean rateStatus;

    @SerializedName("date")
    private Long date;

    @SerializedName("time")
    private String time;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCaregiverName() {
        return caregiverName;
    }

    public void setCaregiverName(String caregiverName) {
        this.caregiverName = caregiverName;
    }

    public String getRegisterNurseNumber() {
        return registerNurseNumber;
    }

    public void setRegisterNurseNumber(String registerNurseNumber) {
        this.registerNurseNumber = registerNurseNumber;
    }

    public Long getCaregiverId() {
        return caregiverId;
    }

    public void setCaregiverId(Long caregiverId) {
        this.caregiverId = caregiverId;
    }

    public Boolean getRateStatus() {
        return rateStatus;
    }

    public void setRateStatus(Boolean rateStatus) {
        this.rateStatus = rateStatus;
    }

    public String getReasonAcceptanceStatus() {
        return reasonAcceptanceStatus;
    }

    public void setReasonAcceptanceStatus(String reasonAcceptanceStatus) {
        this.reasonAcceptanceStatus = reasonAcceptanceStatus;
    }

    public Boolean getAcceptanceStatus() {
        return acceptanceStatus;
    }

    public void setAcceptanceStatus(Boolean acceptanceStatus) {
        this.acceptanceStatus = acceptanceStatus;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
