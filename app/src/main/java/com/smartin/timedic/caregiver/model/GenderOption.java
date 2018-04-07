package com.smartin.timedic.caregiver.model;

/**
 * Created by Hafid on 2/5/2018.
 */

public class GenderOption {
    private Integer iconId;
    private String gender;
    private String background_id;

    public GenderOption(Integer iconId, String gender) {
        this.iconId = iconId;
        this.gender = gender;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(Integer iconId) {
        this.iconId = iconId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBackground_id() {
        return background_id;
    }

    public void setBackground_id(String background_id) {
        this.background_id = background_id;
    }

    @Override
    public String toString() {
        return this.gender;
    }
}
