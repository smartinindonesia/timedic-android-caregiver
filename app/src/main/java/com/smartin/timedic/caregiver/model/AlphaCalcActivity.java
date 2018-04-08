package com.smartin.timedic.caregiver.model;

/**
 * Created by Hafid on 1/14/2018.
 */

public class AlphaCalcActivity {
    private int id;
    private String actname;
    private float alphaValue;
    private int resourceId;

    public AlphaCalcActivity() {
    }

    public AlphaCalcActivity(String actname, float alphaValue) {
        this.actname = actname;
        this.alphaValue = alphaValue;
    }

    public AlphaCalcActivity(int id, String actname, float alphaValue, int resourceId) {
        this.id = id;
        this.actname = actname;
        this.alphaValue = alphaValue;
        this.resourceId = resourceId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public String getActname() {
        return actname;
    }

    public void setActname(String actname) {
        this.actname = actname;
    }

    public void setAlphaValue(float alphaValue) {
        this.alphaValue = alphaValue;
    }

    public float getAlphaValue(){
        return this.alphaValue;
    }

    @Override
    public String toString() {
        return actname;
    }
}
