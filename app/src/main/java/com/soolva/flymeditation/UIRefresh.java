package com.soolva.flymeditation;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;


/**
 * Created by angel on 6.4.2017 г..
 */
// ще обновявам това на всеки половин секунда
public class UIRefresh extends Thread {

    private int uPeriodMillis;
    Handler local;
    long uStartTime;
    long previousUpdate;
    long now;
    private Bundle uiBundle;
    float VerticalRaw;
    float VerticalAVG;
    float Altitude;
    currentState uiCurrentState;



    public UIRefresh(Handler mainActivityHandler, currentState curentSate, int i) {
        local= mainActivityHandler;
        uiCurrentState=curentSate;
        uPeriodMillis=i;
        uiBundle=new Bundle();
        now= System.currentTimeMillis();
        previousUpdate=now-500;
    }


    @Override
    public void run(){
        try {

            while(true){
                now= System.currentTimeMillis();
                uiBundle.putFloat("AvgVerticalSpeed",uiCurrentState.getcAvgVerticalSeed1());
                uiBundle.putFloat("AvgVerticalChange",uiCurrentState.getcAvgVerticalSpeedChange());
                uiBundle.putFloat("RawVerticalSpeed",uiCurrentState.getcRawVerticalSpeed());
                uiBundle.putFloat("Altitude",uiCurrentState.getcAltitude());
                uiBundle.putFloat("AvgVerticalSpeed2",uiCurrentState.getcAvgVerticalSeed2());
                uiBundle.putDouble("Disp",uiCurrentState.getcDispersion1());
                uiBundle.putDouble("Disp2",uiCurrentState.getcDispersion2());
                uiBundle.putInt("Minutes",uiCurrentState.getMinutes());
                uiBundle.putInt("Seconds",uiCurrentState.getSeconds());

                Message message = Message.obtain();
                message.arg1 = 2;
                message.arg2=2;
                message.obj ="Iron Man";
                message.setData(uiBundle);
                local.sendMessage(message);
                previousUpdate=now;
                Log.i("In my thread", String.valueOf(uiBundle));
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        }
}
