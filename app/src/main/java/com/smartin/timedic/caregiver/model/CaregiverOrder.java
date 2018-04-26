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
    @SerializedName("registerNurseNumber")
    private String registerNurseNumber;
    @SerializedName("idCaregiver")
    private Long caregiverId;
    @SerializedName("rateStatus")
    private Boolean rateStatus;

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
}
