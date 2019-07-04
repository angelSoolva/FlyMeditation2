package com.soolva.flymeditation;

/**
 * Created by angel on 9.4.2017 г..
 */

public class currentState {

    private float cRawVerticalSpeed;
    private float cAvgVerticalSpeedChange;
    private double cDispersion1;
    private double cDispersion2;



    private float cAvgVerticalSeed1;
    private float cAvgVerticalSeed2;

    private float cAltitude;
    private float cStableAltitude;

   // private fileWriteRead cfileWrite;
    private int seconds;
    private int minutes;



    public currentState() {

    }

    public float getcAvgVerticalSpeedChange() {
        return cAvgVerticalSpeedChange;
    }

    public void setcAvgVerticalSpeedChange(float cAvgVerticalSpeedChange) {
        this.cAvgVerticalSpeedChange = cAvgVerticalSpeedChange;
    }

    public void setcRawVerticalSpeed(float cRawVerticalSpeed) {
        this.cRawVerticalSpeed = cRawVerticalSpeed;
    }

    public void setcDispersion1(double cDispersion1) {
        this.cDispersion1 = cDispersion1;
    }

    public void setcDispersion2(double cDispersion2) {
        this.cDispersion2 = cDispersion2;
    }

    public void setcAvgVerticalSeed1(float cAvgVerticalSeed1) {
        this.cAvgVerticalSeed1 = cAvgVerticalSeed1;
    }

    public void setcAvgVerticalSeed2(float cAvgVerticalSeed2) {
        this.cAvgVerticalSeed2 = cAvgVerticalSeed2;
    }

    public void setcAltitude(float cAltitude) {
        this.cAltitude = cAltitude;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public float getcRawVerticalSpeed() {
        return cRawVerticalSpeed;
    }

    public float getcAvgVerticalSeed1() {
        return cAvgVerticalSeed1;
    }

    public float getcAvgVerticalSeed2() {
        return cAvgVerticalSeed2;
    }

    public double getcDispersion1() {
        return cDispersion1;
    }

    public double getcDispersion2() {
        return cDispersion2;
    }

    public float getcAltitude() {
        return cAltitude;
    }

    public float getcStableAltitude() {
        return cStableAltitude;
    }

    public int getSeconds() {
        return seconds;
    }

    public int getMinutes() {
        return minutes;
    }



}



