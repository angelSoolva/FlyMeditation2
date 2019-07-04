package com.soolva.flymeditation;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by angel on 25.3.2017 г..
 */

public class fileWriteRead {
    String lFilename;
    String lDirectory;
    Long rStartTime;
    File myFile;
    int counter;
    int NumberOfWriteCycle;
    boolean lRecordActive;
    boolean lThermal;






    public fileWriteRead(String directory){

        lDirectory=directory;
        rStartTime= System.currentTimeMillis();
        lRecordActive=false;
        NumberOfWriteCycle=0;


        File newDIR = new File(Environment.getExternalStorageDirectory(),directory);

        if (!newDIR.exists()) {
            newDIR.mkdirs();
        }

    }

    public boolean StartRecording(String fileName) throws IOException {
        if(lRecordActive){

            Log.i("Record already Active","file Writer");
            return false;
        }
        NumberOfWriteCycle++;
        String mFile=fileName;
        DateFormat df = new SimpleDateFormat("MMddyyyy");
        String now = df.format(new Date());
        Calendar rightNow = Calendar.getInstance();
        int hour = rightNow.get(Calendar.HOUR_OF_DAY);
        int minutes=rightNow.get(Calendar.MINUTE);
        int seconds = rightNow.get(Calendar.SECOND);
        lFilename = mFile+NumberOfWriteCycle+"N"+ now+hour+minutes+seconds+".csv";
        myFile = new File(Environment.getExternalStorageDirectory(),lDirectory+lFilename);
        myFile.delete();

        myFile.createNewFile();

        lRecordActive=true;

        writeRoll("");
        writeRoll("0,Begin of recording data No.:"+NumberOfWriteCycle);
        String dataWrite = "0,Start of recording session: "+ System.currentTimeMillis();
        writeRoll(dataWrite);
        writeRoll("");
        return true;
    }
    public boolean StopRecording(){
        lRecordActive=false;
        return true;
    }
    public boolean StartThermal(){
        lThermal=true;
        return lThermal;
    }
    public boolean StopThermal(){
        lThermal=false;
        return lThermal;
    }


    public boolean fWrite(String dataW){
        if(!lRecordActive){return false;}
        if(counter == 0){counter = 1;}
        else {counter++;}


        //long millis = System.currentTimeMillis();
        //int seconds = (int) (millis / 1000);
        dataW=counter+","+dataW+","+lThermal;
        writeRoll(dataW);
        return true;
    }
    private boolean writeRoll(String RollData){
        RollData=RollData+"\n";
        try {
            File myFile = new File(Environment.getExternalStorageDirectory(),lDirectory+lFilename);
            FileOutputStream fOut = new FileOutputStream(myFile,true);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);

            myOutWriter.append(RollData);
            myOutWriter.close();
            fOut.close();
            // FileOutputStream fOut = openFileOutput(file, MODE_WORLD_READABLE);
            // fOut.write(dataW.getBytes());
            //fOut.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public String fRead() {
        String tmp;
        String rWord="";

        String rCol1;
        String rCol2;
        String rCol3;

        char rTemp=' ';
        //miBeginR= Integer.parseInt(mBeginRow.getText().toString());
        //miEndR= Integer.parseInt(mEndRow.getText().toString());
        int rLength=0;
        int rComma1=0;
        int rComma2=0;
        int rCol=1;

        //Get the text file
        File lFile = new File(Environment.getExternalStorageDirectory(),lDirectory+lFilename);
        //Read text from file
        StringBuilder text = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(lFile));
            String line;

            while ((line=br.readLine()) !=null) {

                rLength = line.length();
                for (int i = 0; i < rLength; i++) {

                    rTemp = line.charAt(i);

                    if (rTemp == ',') {
                        if (rCol == 1) {
                            rComma1 = i;
                            rCol++;
                        } else {
                            rComma2 = i;
                            rCol = 1;

                        }
                    }
                }
                rCol1 = line.substring(0,rComma1);
                rCol2 = line.substring(rComma1,rComma2);
                rCol3 = line.substring(rComma2,rLength-1);

                //rWord = rWord + rTemp;




              //  if (Integer.parseInt(rCol1) >= miBeginR && Integer.parseInt(rCol1) <= miEndR) {
                    text.append(line);
                    text.append('\n');
               // }
            }

            br.close();

            // FileInputStream fin = openFileInput(file);
            // int c;
            // temp = "";
            // while ((c = fin.read()) != -1) {
            //    temp = temp + Character.toString((char) c);
            // }

        } catch (Exception e) {
        }
        tmp=text.toString();

        return tmp;
    }



}

