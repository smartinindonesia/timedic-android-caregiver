package com.smartin.timedic.caregiver.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Hafid on 1/20/2018.
 */

public class PaymentMethod implements Serializable {
    @SerializedName("id")
    private Long id;
    @SerializedName("paymentMethod")
    private String paymentMethodName;
    @SerializedName("description")
    private String description;

    public PaymentMethod(Long id) {
        this.id = id;
    }

    public PaymentMethod(Long id, String paymentMethodName) {
        this.id = id;
        this.paymentMethodName = paymentMethodName;
    }

    public PaymentMethod() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPaymentMethodName() {
        return paymentMethodName;
    }

    public void setPaymentMethodName(String paymentMethodName) {
        this.paymentMethodName = paymentMethodName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
