package com.soolva.flymeditation;


import android.util.Log;

/**
 * Created by angel on 31.3.2017 г..
 */

public class DataRetrieve {
    private static final String TAG = "DataRetrieve";
    boolean sentenceAssemblyActive;
    StringBuilder dCurent;
    fileWriteRead lFileWriter;
    String LOG="DataRetrieve";
    DataInterpreter mDataInterpreter;

    boolean starting;
    Global_Data_Class mGlobalClass;



    public DataRetrieve(Global_Data_Class dataClass, DataInterpreter EP ) {
        sentenceAssemblyActive = false;
        dCurent=new StringBuilder();
        starting=true;
        mGlobalClass = dataClass;
        mDataInterpreter =EP;

    }
        public boolean stringProcess(String partSentence) {
            String sTemp;
            int sLenght;
            int sSBLenght;
            Log.i(TAG,  "Sentance recieved: " + partSentence);
           // char[] endChar=new char[1];
                    sLenght = partSentence.length();
           // partSentence.getChars(sLenght-1,sLenght-1,endChar,0);

            if(partSentence.contains("\n")){
                sTemp=partSentence.substring(0,sLenght-2);
            }else{
                sTemp=partSentence;
            }

            if (sTemp.startsWith("$XCTRC")) {
                if (!sentenceAssemblyActive) {
                    dCurent.append(sTemp);
                    sentenceAssemblyActive = true;
                    return true;
                } else {

                    finishSentance();
                    dCurent.append(sTemp);
                    return true;
                }
            }
            if(sentenceAssemblyActive){
                dCurent.append(sTemp);
            }



        return  true;
        }

        public boolean finishSentance(){
            int sSBLenght;
            String aaa= dCurent.toString();

            Log.i(TAG,"Kolko e golqm stringa? :" + aaa);
     //       lFileWriter.fWrite(aaa);
            getData(aaa);
            sSBLenght=dCurent.length();
            dCurent.delete(0,sSBLenght);



            return true;

        }

    private void getData(String xcTraceSentance) {
        char rTemp;
        int commaCount=0;
        int rLength = xcTraceSentance.length();
        int verticalBegin=0;
        int verticalEnd=0;
        int altitudeBegin=0;
        int altitudeEnd=0;
        float verticalSpeed=0F;
        float altitudeF=0F;
        String vertical;
        String altitude;

        for (int i = 0; i < rLength; i++) {
             rTemp = xcTraceSentance.charAt(i);

            if (rTemp == ',') {
                commaCount++;
                if (commaCount == 13) {
                    verticalBegin = i+1;
                }
                if (commaCount == 14) {
                    verticalEnd = i;
                    break;
                }

                if (commaCount == 10) {
                    altitudeBegin = i+1;
                }
                if (commaCount == 11) {
                    altitudeEnd = i;
                    //break;
                }
            }
        }
        vertical=xcTraceSentance.substring(verticalBegin,verticalEnd);

        verticalSpeed= Float.parseFloat(vertical);

        Log.i("Vertical Speed value",vertical);


        //dAvgVerticalSpeed.pushRawVertical()


        Log.i(TAG, "Vertical speed:"+ Float.toString(verticalSpeed));
        //lFileWriter.fWrite(Float.toString(verticalSpeed));

        altitude=xcTraceSentance.substring(altitudeBegin,altitudeEnd);
        altitudeF= Float.parseFloat(altitude);
        // Calling Application class (see application tag in AndroidManifest.xml)



        //Get Altitude and vertical speed
        mGlobalClass.setAltitude(altitudeF);
        mGlobalClass.setVerticalSpeed(verticalSpeed);
        Log.i(TAG, "Altitude:"+ Float.toString(altitudeF));
        Log.i(TAG, "Altitude from glClass" + mGlobalClass.getAltitude() + "and ver speed" + mGlobalClass.getVerticalSpeed());

//Tuka ste dobavq dчете от файл бабешката

     mDataInterpreter.pushValues(verticalSpeed, altitudeF);





       // lFileWriter.fWrite(Float.toString(verticalSpeed)+","+Float.toString(altitudeF));
    }


}

