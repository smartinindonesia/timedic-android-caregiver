package com.smartin.timedic.caregiver.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Hafid on 26/04/2018.
 */

public class OrderItem implements Serializable {
    @SerializedName("id")
    private Long id;
    @SerializedName("caregiverName")
    private String caregiverName;
    @SerializedName("registerNurseNumber")
    private String registerNurseNumber;
    @SerializedName("day")
    private String day;
    @SerializedName("time")
    private String time;
    @SerializedName("rateStatus")
    private Boolean isRated;
    @SerializedName("idCaregiver")
    private Long idCaregiver;
    @SerializedName("idTransaction")
    private Long idTransaction;
    @SerializedName("idHomecareClinic")
    private HomecareClinic idHomecareClinic;

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

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Boolean getRated() {
        return isRated;
    }

    public void setRated(Boolean rated) {
        isRated = rated;
    }

    public Long getIdCaregiver() {
        return idCaregiver;
    }

    public void setIdCaregiver(Long idCaregiver) {
        this.idCaregiver = idCaregiver;
    }

    public Long getIdTransaction() {
        return idTransaction;
    }

    public void setIdTransaction(Long idTransaction) {
        this.idTransaction = idTransaction;
    }

    public HomecareClinic getIdHomecareClinic() {
        return idHomecareClinic;
    }

    public void setIdHomecareClinic(HomecareClinic idHomecareClinic) {
        this.idHomecareClinic = idHomecareClinic;
    }
}