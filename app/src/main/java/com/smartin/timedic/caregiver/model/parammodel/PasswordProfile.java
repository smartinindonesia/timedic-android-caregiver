package com.smartin.timedic.caregiver.model.parammodel;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Hafid on 3/20/2018.
 */

public class PasswordProfile {
    @SerializedName("password")
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean passwordValidator(String password) {
        return this.password.equals(password);
    }
}
