package com.smartin.timedic.caregiver.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Hafid on 3/21/2018.
 */

public class AppSetting {
    @SerializedName("notification")
    private Boolean active = false;
    @SerializedName("languange")
    private String selectedLanguage = "";

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getSelectedLanguage() {
        return selectedLanguage;
    }

    public void setSelectedLanguage(String selectedLanguage) {
        this.selectedLanguage = selectedLanguage;
    }
}
