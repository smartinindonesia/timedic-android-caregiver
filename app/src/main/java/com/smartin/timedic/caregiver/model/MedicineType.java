package com.smartin.timedic.caregiver.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Hafid on 07/05/2018.
 */

public class MedicineType {
    @SerializedName("id")
    private Integer id;
    @SerializedName("medicineType")
    private String medicineType;
    @SerializedName("icon")
    private Integer icon;

    public MedicineType(Integer id, String medicineType, Integer icon) {
        this.id = id;
        this.medicineType = medicineType;
        this.icon = icon;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMedicineType() {
        return medicineType;
    }

    public void setMedicineType(String medicineType) {
        this.medicineType = medicineType;
    }

    public Integer getIcon() {
        return icon;
    }

    public void setIcon(Integer icon) {
        this.icon = icon;
    }
}
