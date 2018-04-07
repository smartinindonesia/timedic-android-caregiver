package com.smartin.timedic.caregiver.model.uimodel;

import java.io.Serializable;

/**
 * Created by Hafid on 2/9/2018.
 */

public class Slider implements Serializable{
    private int image_source_id;
    private String image_uri;
    private String description;

    public Slider() {
    }

    public Slider(String image_uri, String description) {
        this.image_uri = image_uri;
        this.description = description;
    }

    public Slider(int image_source_id, String description) {
        this.image_source_id = image_source_id;
        this.description = description;
    }

    public int getImage_source_id() {
        return image_source_id;
    }

    public void setImage_source_id(int image_source_id) {
        this.image_source_id = image_source_id;
    }

    public String getImage_uri() {
        return image_uri;
    }

    public void setImage_uri(String image_uri) {
        this.image_uri = image_uri;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
