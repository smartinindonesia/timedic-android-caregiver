package com.smartin.timedic.caregiver.customuicompt;

import android.widget.ToggleButton;

/**
 * Created by Hafid on 10/29/2017.
 */

public class ButtonModel<T> {
    private T info;
    public ToggleButton button;
    private boolean on;

    public T getInfo() {
        return info;
    }

    public void setInfo(T info) {
        this.info = info;
    }

    public ToggleButton getButton() {
        return button;
    }

    public void setButton(ToggleButton button) {
        this.button = button;
    }

    public boolean isOn() {
        return on;
    }

    public void setOn(boolean on) {
        this.on = on;
    }
}
