package com.smartin.timedic.caregiver.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Hafid on 1/11/2018.
 */

public class AssessmentOption {
    @SerializedName("id")
    private int id;
    @SerializedName("option")
    private String option;
    @SerializedName("priceAdded")
    private Double priceAdded;

    public Double getPriceAdded() {
        if (priceAdded != null) {
            return priceAdded;
        } else {
            return 0.0;
        }
    }

    public void setPriceAdded(Double priceAdded) {
        this.priceAdded = priceAdded;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }
}
