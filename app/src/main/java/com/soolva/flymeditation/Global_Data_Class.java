package com.soolva.flymeditation;

import android.app.Application;

public class Global_Data_Class extends Application {
    private float Altitude;
    private float VerticalSpeed;
    private float upStepValue;
    private float downStepValue;





    public Global_Data_Class() {
        Altitude = -1F;
        VerticalSpeed = -1000F;
        upStepValue = 2F;
        downStepValue = 2F;
    }
    public float getUpStepValue() {
        return upStepValue;
    }

    public void setUpStepValue(float upStepValue) {
        this.upStepValue = upStepValue;
    }

    public float getDownStepValue() {
        return downStepValue;
    }

    public void setDownStepValue(float downStepValue) {
        this.downStepValue = downStepValue;
    }

    public float getAltitude() {
                return Altitude;
    }

    public void setAltitude(float altitude) {
        Altitude = altitude;
    }

    public float getVerticalSpeed() {
        return VerticalSpeed;
    }

    public void setVerticalSpeed(float verticalSpeed) {
        VerticalSpeed = verticalSpeed;
    }
}
