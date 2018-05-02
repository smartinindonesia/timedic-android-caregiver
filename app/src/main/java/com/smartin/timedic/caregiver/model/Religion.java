package com.smartin.timedic.caregiver.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Hafid on 02/05/2018.
 */

public class Religion {
    @SerializedName("id")
    private Long id;
    @SerializedName("religionName")
    private String religion;

    public Religion(Long id, String religion) {
        this.id = id;
        this.religion = religion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }
}
