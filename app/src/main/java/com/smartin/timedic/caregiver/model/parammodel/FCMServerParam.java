package com.smartin.timedic.caregiver.model.parammodel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Hafid on 02/05/2018.
 */

public class FCMServerParam implements Serializable{
    @SerializedName("fcmToken")
    private String fcmToken;

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }
}
