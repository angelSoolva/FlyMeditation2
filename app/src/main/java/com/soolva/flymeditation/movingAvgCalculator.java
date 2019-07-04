package com.soolva.flymeditation;

import android.util.Log;

/**
 * Created by angel on 3.4.2017 г..
 */

public class movingAvgCalculator {
    private float circularBuffer[];
    private float avg;
    private float rawValue;
    private int circularIndex;
    private int count;
    //Add dispersion and variation
    private float variation;



    private double stddev;


    public movingAvgCalculator(int k){
        circularBuffer = new float[k];
        count = 0;
        circularIndex =0;
        avg=0;
        variation=0f;
        stddev=0f;

    }
    // Get current moving averrage
    public float getAvg()
    {
        Log.i("Inside GETAVG", String.valueOf(avg));

        return avg;
    }
    public float getRawValue()
    {
        return rawValue;
    }
    public double getStddev() {
        return stddev;
    }
    public void pushValue (float x)
    {
        rawValue =x;
        Log.i("Verticla spped recieved", String.valueOf(rawValue));

        float previousAVG;
        if (count++==0)
        {
            primeBuffer(x);
        }
        float lastValue = circularBuffer[circularIndex];
        previousAVG=avg;

        avg=avg+(x-lastValue)/circularBuffer.length;
        Log.i("AVRG CALCULATED", String.valueOf(avg));
        variation+=(x-lastValue)*(x-avg+lastValue-previousAVG);

        stddev= Math.sqrt(variation);
        Log.i("STANDARD DEV", String.valueOf(stddev));


        circularBuffer[circularIndex]=x;
        circularIndex=nextIndex(circularIndex);
    }
    public long getCount()
    {
        return count;
    }
    private void primeBuffer(float val)
    {
        for(int i=0; i< circularBuffer.length;++i)
        {
            circularBuffer[i]=val;
        }
        avg=val;
    }
    private int nextIndex(int curIndex)
    {
        if (curIndex+1>= circularBuffer.length)
        {
            return 0;
        }
        return curIndex+1;

    }
    }



