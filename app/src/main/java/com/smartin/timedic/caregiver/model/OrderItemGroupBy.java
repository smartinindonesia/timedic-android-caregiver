package com.smartin.timedic.caregiver.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by Hafid on 26/04/2018.
 */

public class OrderItemGroupBy implements Serializable {

    @SerializedName("idCaregiver")
    private Long idCaregiver;

    @SerializedName("idServiceTrx")
    private Long idServiceTrx;

    @SerializedName("orderNumber")
    private String orderNumber;

    @SerializedName("orderName")
    private String orderName;

    @SerializedName("selectedService")
    private String selectedService;

    public Long getIdCaregiver() {
        return idCaregiver;
    }

    public void setIdCaregiver(Long idCaregiver) {
        this.idCaregiver = idCaregiver;
    }

    public Long getIdServiceTrx() {
        return idServiceTrx;
    }

    public void setIdServiceTrx(Long idServiceTrx) {
        this.idServiceTrx = idServiceTrx;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getSelectedService() {
        return selectedService;
    }

    public void setSelectedService(String selectedService) {
        this.selectedService = selectedService;
    }
}