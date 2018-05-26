package com.smartin.timedic.caregiver.model.parammodel;

import android.text.TextUtils;
import android.util.Patterns;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Hafid on 1/1/2018.
 */

public class RegisterParam implements Serializable{

    @SerializedName("username")
    private String username;
    @SerializedName("password")
    private String password;
    @SerializedName("frontName")
    private String firstname;
    @SerializedName("middleName")
    private String middlename;
    @SerializedName("lastName")
    private String lastname;
    @SerializedName("address")
    private String address;
    @SerializedName("phoneNumber")
    private String phone;
    @SerializedName("email")
    private String email;
    @SerializedName("dateOfBirth")
    private Long dateOfBirth;
    @SerializedName("firebaseIdFacebook")
    private String firebaseIdFacebook;
    @SerializedName("firebaseIdGoogle")
    private String firebaseIdGoogle;
    @SerializedName("gender")
    private String gender;
    @SerializedName("religion")
    private String religion;
    @SerializedName("firebaseIdByEmail")
    private String firebaseIdByEmail;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Long dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public boolean isValidPhone() {
        boolean isTrue = android.util.Patterns.PHONE.matcher(phone).matches();
        return isTrue;
    }

    public boolean isValidUsername() {
        if (username != null) {
            if (username.trim().isEmpty()) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public boolean isUsernameContainSpace(){
        if (username.contains(" ")){
            return true;
        } else {
            return false;
        }
    }

    public boolean isFirstNameEmpty(){
        if (firstname != null) {
            if (firstname.trim().isEmpty()) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    public boolean isValidEmail() {
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    public boolean passwordValidator(String password){
        return this.password.equals(password);
    }

    public String getFirebaseIdFacebook() {
        return firebaseIdFacebook;
    }

    public void setFirebaseIdFacebook(String firebaseIdFacebook) {
        this.firebaseIdFacebook = firebaseIdFacebook;
    }

    public String getFirebaseIdGoogle() {
        return firebaseIdGoogle;
    }

    public void setFirebaseIdGoogle(String firebaseIdGoogle) {
        this.firebaseIdGoogle = firebaseIdGoogle;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getFirebaseIdByEmail() {
        return firebaseIdByEmail;
    }

    public void setFirebaseIdByEmail(String firebaseIdByEmail) {
        this.firebaseIdByEmail = firebaseIdByEmail;
    }
}
