package com.soolva.flymeditation;

import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by angel on 17.4.2017 г..
 */

public class fileRead {
    String fDirectory;
    String file="1.csv";
    boolean active;
    float verticalSpeedRead=0;
    float AltitudeRead=0;
    int fSeconds=0;
    int fMinutes=0;
    public BufferedReader br;



    public fileRead(String directory)
    {
        fDirectory=directory;

    }
    public boolean getState(){
        return active;
    }
    public float getVerticalSpeed(){
        return verticalSpeedRead;
    }
    public float getAltitude()
    {
        return AltitudeRead;
    }

    public void frStart()
    {
        openFile();
        active=true;
    }
    public void frStop(){
        closeFile();
        active=false;
    }
    public void frPause(){
        active= false;
    }
    public void frRestart(){
        if(br!=null){active=true;}
    }







    private void openFile() {
        //Get the text file
        File lFile = new File(Environment.getExternalStorageDirectory(),fDirectory+file);
        //Read text from file
        //StringBuilder text = new StringBuilder();
        try {
            br = new BufferedReader(new FileReader(lFile));
        } catch (Exception e) {
        }
            }
    private void closeFile() {
            try {
               if(br!=null){
                   br.close();
               }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return;
    }

    public int getSeconds() {
        return fSeconds;
    }
    public int getMinutes() {
        return fMinutes;
    }
    public void Read() {


        char rTemp = ' ';
        //miBeginR= Integer.parseInt(mBeginRow.getText().toString());
        //miEndR= Integer.parseInt(mEndRow.getText().toString());
        int rLength = 0;


        int commaCount = 0;
        int verticalBegin = 0;
        int verticalEnd = 0;
        int altitudeBegin = 0;
        int altitudeEnd = 0;
        int minutesBegin = 0;
        int minutesEnd = 0;
        int secondsBegin=0;
        int secondsEnd = 0;
        String vertical;
        String altitude;
        String minutes;
        String seconds;


        String line;
        if (active) {


            try {
                if ((line = br.readLine()) != null) {

                    rLength = line.length();
                    for (int i = 0; i < rLength; i++) {
                        rTemp = line.charAt(i);

                        if (rTemp == ',') {

                            commaCount++;
                            switch(commaCount){

                                case 2: minutesBegin=i+1;
                                case 3: {
                                    minutesEnd=i;
                                    secondsBegin=i+1;
                                }

                                case 4: {
                                    secondsEnd=i;
                                    verticalBegin = i + 1;
                                }
                                case 5: verticalEnd = i;
                                case 7: altitudeBegin = i + 1;
                                case 8: altitudeEnd = i;


                            }
                        }
                    }
                    if(commaCount>8) {
                        vertical = line.substring(verticalBegin, verticalEnd);

                        verticalSpeedRead = Float.parseFloat(vertical);
                        altitude = line.substring(altitudeBegin, altitudeEnd);
                        AltitudeRead = Float.parseFloat(altitude);

                        minutes = line.substring(minutesBegin, minutesEnd);
                        fMinutes = Integer.parseInt(minutes);
                        seconds = line.substring(secondsBegin, secondsEnd);
                        fSeconds = Integer.parseInt(seconds);
                    }

                }


            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }
    }
}
