package com.smartin.timedic.caregiver.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by Hafid on 9/8/2017.
 */

public class User implements Serializable{
    public static final String TAG = "[User]";

    @SerializedName("id")
    private Long id;
    @SerializedName("address")
    private String address;
    @SerializedName("dateBirth")
    private String dateBirth;
    @SerializedName("email")
    private String email;
    @SerializedName("frontName")
    private String frontName;
    @SerializedName("lastName")
    private String lastName;
    @SerializedName("middleName")
    private String middleName;
    @SerializedName("password")
    private String password;
    @SerializedName("phoneNumber")
    private String phoneNumber;
    @SerializedName("photoPath")
    private String photoPath;
    @SerializedName("username")
    private String username;
    @SerializedName("firstRegistrationDate")
    private String firstRegistrationDate;
    @SerializedName("latitude")
    private float latitude;
    @SerializedName("longitude")
    private float longitude;
    @SerializedName("roles")
    private String[] roles;
    @SerializedName("accountNonExpired")
    private boolean accountNonExpired;
    @SerializedName("accountNonLocked")
    private boolean accountNonLocked;
    @SerializedName("credentialsNonExpired")
    private boolean credentialsNonExpired;
    @SerializedName("enabled")
    private boolean enabled;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(String dateBirth) {
        this.dateBirth = dateBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFrontName() {
        return frontName;
    }

    public void setFrontName(String frontName) {
        this.frontName = frontName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstRegistrationDate() {
        return firstRegistrationDate;
    }

    public void setFirstRegistrationDate(String firstRegistrationDate) {
        this.firstRegistrationDate = firstRegistrationDate;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", dateBirth='" + dateBirth + '\'' +
                ", email='" + email + '\'' +
                ", frontName='" + frontName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", password='" + password + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", photoPath='" + photoPath + '\'' +
                ", username='" + username + '\'' +
                ", firstRegistrationDate='" + firstRegistrationDate + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", roles=" + Arrays.toString(roles) +
                ", accountNonExpired=" + accountNonExpired +
                ", accountNonLocked=" + accountNonLocked +
                ", credentialsNonExpired=" + credentialsNonExpired +
                ", enabled=" + enabled +
                '}';
    }
}
