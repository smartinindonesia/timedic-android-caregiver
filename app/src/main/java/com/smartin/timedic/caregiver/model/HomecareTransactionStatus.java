package com.smartin.timedic.caregiver.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Hafid on 1/20/2018.
 */

public class HomecareTransactionStatus implements Serializable {
    @SerializedName("id")
    private Long id;
    @SerializedName("status")
    private String status;

    public HomecareTransactionStatus(Long id) {
        this.id = id;
    }

    public HomecareTransactionStatus(Long id, String status) {
        this.id = id;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
