package com.smartin.timedic.caregiver.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Hafid on 4/19/2018.
 */

public class CaregiverRate implements Serializable{
    @SerializedName("id")
    private Long id;
    @SerializedName("rate")
    private Integer rate;
    @SerializedName("comment")
    private String comment;
    @SerializedName("idHomeCareTransaction")
    private Long idHomeCareTransaction;
    @SerializedName("idAppUser")
    private User idAppUser;
    @SerializedName("idHomecareCaregiver")
    private Caregiver idHomecareCaregiver;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getIdHomeCareTransaction() {
        return idHomeCareTransaction;
    }

    public void setIdHomeCareTransaction(Long idHomeCareTransaction) {
        this.idHomeCareTransaction = idHomeCareTransaction;
    }

    public User getIdAppUser() {
        return idAppUser;
    }

    public void setIdAppUser(User idAppUser) {
        this.idAppUser = idAppUser;
    }

    public Caregiver getIdHomecareCaregiver() {
        return idHomecareCaregiver;
    }

    public void setIdHomecareCaregiver(Caregiver idHomecareCaregiver) {
        this.idHomecareCaregiver = idHomecareCaregiver;
    }
}
