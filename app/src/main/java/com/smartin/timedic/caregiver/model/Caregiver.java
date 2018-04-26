package com.smartin.timedic.caregiver.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Hafid on 9/23/2017.
 */

public class Caregiver implements Serializable{
    public static String TAG = "[Caregiver]";

    @SerializedName("id")
    private Long id;
    @SerializedName("address")
    private String address;
    @SerializedName("dateOfBirth")
    private Long dateOfBirth;
    @SerializedName("email")
    private String email;
    @SerializedName("employeeIdNumber")
    private String employeeIdNumber;
    @SerializedName("firstRegistrationDate")
    private Long firstRegistrationDate;
    @SerializedName("frontName")
    private String frontName;
    @SerializedName("middleName")
    private String middleName;
    @SerializedName("lastName")
    private String lastName;
    @SerializedName("password")
    private String password;
    @SerializedName("phoneNumber")
    private String phoneNumber;
    @SerializedName("photoPath")
    private String photoPath;
    @SerializedName("registerNurseNumber")
    private String registerNurseNumber;
    @SerializedName("sipp")
    private String sipp;
    @SerializedName("username")
    private String username;
    @SerializedName("idCaregiverStatus")
    private CaregiverStatus idCaregiverStatus;
    @SerializedName("idHomecareClinic")
    private HomecareClinic idHomecareClinic;

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

    public Long getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Long dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmployeeIdNumber() {
        return employeeIdNumber;
    }

    public void setEmployeeIdNumber(String employeeIdNumber) {
        this.employeeIdNumber = employeeIdNumber;
    }

    public Long getFirstRegistrationDate() {
        return firstRegistrationDate;
    }

    public void setFirstRegistrationDate(Long firstRegistrationDate) {
        this.firstRegistrationDate = firstRegistrationDate;
    }

    public String getFrontName() {
        return frontName;
    }

    public void setFrontName(String frontName) {
        this.frontName = frontName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getRegisterNurseNumber() {
        return registerNurseNumber;
    }

    public void setRegisterNurseNumber(String registerNurseNumber) {
        this.registerNurseNumber = registerNurseNumber;
    }

    public String getSipp() {
        return sipp;
    }

    public void setSipp(String sipp) {
        this.sipp = sipp;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public CaregiverStatus getIdCaregiverStatus() {
        return idCaregiverStatus;
    }

    public void setIdCaregiverStatus(CaregiverStatus idCaregiverStatus) {
        this.idCaregiverStatus = idCaregiverStatus;
    }

    public HomecareClinic getIdHomecareClinic() {
        return idHomecareClinic;
    }

    public void setIdHomecareClinic(HomecareClinic idHomecareClinic) {
        this.idHomecareClinic = idHomecareClinic;
    }
}
