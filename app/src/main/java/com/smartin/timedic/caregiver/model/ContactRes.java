package com.smartin.timedic.caregiver.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Hafid on 02/05/2018.
 */

public class ContactRes implements Serializable{

    @SerializedName("id")
    private Long id;
    @SerializedName("phoneOffice")
    private String phoneOffice;
    @SerializedName("mobilePhone")
    private String mobilePhone;
    @SerializedName("name")
    private String name;
    @SerializedName("email")
    private String email;
    @SerializedName("facebookLink")
    private String facebookLink;
    @SerializedName("twitterLink")
    private String twitterLink;
    @SerializedName("instagramLink")
    private String instagramLink;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoneOffice() {
        return phoneOffice;
    }

    public void setPhoneOffice(String phoneOffice) {
        this.phoneOffice = phoneOffice;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFacebookLink() {
        return facebookLink;
    }

    public void setFacebookLink(String facebookLink) {
        this.facebookLink = facebookLink;
    }

    public String getTwitterLink() {
        return twitterLink;
    }

    public void setTwitterLink(String twitterLink) {
        this.twitterLink = twitterLink;
    }

    public String getInstagramLink() {
        return instagramLink;
    }

    public void setInstagramLink(String instagramLink) {
        this.instagramLink = instagramLink;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

