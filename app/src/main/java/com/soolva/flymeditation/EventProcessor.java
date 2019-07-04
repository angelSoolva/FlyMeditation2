package com.soolva.flymeditation;

import android.content.Context;
import android.util.Log;

/**
 * Created by angel on 9.4.2017 г..
 */

public class EventProcessor {
    //File read event
    boolean readFromFile=true; //Тука ръчно му казвам да чете от файлa или не

    //Objects
        SoundPlayer eSoundPlayer;
        fileWriteRead cfileWrite;
        fileRead dfileRead;
        currentState CurrentState;

        private movingAvgCalculator cAvgVerticalSppedCalculator1;
        private movingAvgCalculator cAvgVerticalSppedCalculator2;
        private movingAvgCalculator eAvgAccelerationChange;
//general
    private int eState;
    int counterGLO;

    private int ePreviousState;
    int eStarting=0;
    final int eStable=1;
    int eAcceleratingUp =2;
    int eAcceleratingDown = 3;
    int eDecelaratingUp=4;
    int eDecelaratingDown=5;
    int eAttention;
//Параметри от XCTracer-a


    //Първични параметри
    float eVerticalSpeedXCT;
    float eVerticalSpeedXCTold;
    float eAltitudeXCT;

    //ПАраметри на базата на старите такива
    float eVerticalSpeedChangeOld;
    float eVerticalSpeedChange;

    float eAcceleration;
    float eAccelerationChange;

    private int cAvgValuesCountv1; //Средно стойности, които се взимат за калкулация на средната скорост
    private int cAvgValuesCountv2;
    private double cDispersion1;
    private double cDispersion1old;
    private double cDispersion2;
    private double cDispersion2old;

    private float cAvgVerticalSeed1;
    private float cAvgVerticalSeed2;

    private float verticalSpeedCountValue=0.3f; //will count on 0.5 one impulse
    private int currentVerticalSpeedCount=0;
    private int counterTimesAboveVerticalSpeed; //will count how many times the vertical speed has exceeded the value

    //Параметри които описват състоянието
    long currentConditionTimer;
    long currentEventStart;
    float eventStartAltitude;
    float eventCurrentAltitude;
    float eventStartVertical;
    float eventCurrentVertical;
    //Параметри описващи вертикалната ситуация. Вертикалните ситуации са три вида изкачване на горе, слизане на долу, междинна ситуация
    //- ситуация в която се качваме/слизаме  на долу или на горе по малко. Параметрите са следните: време за отчитане - на колко секунди се отчита
    //Ползват се от zeroUpDownProcessor()
    //Параметри:
    int tCycleSeconds=5; //Константа - времето за което ще се отчита
    int numberOfCycles=tCycleSeconds*5; //Колко ще броим
    float upHeightM=2.0f; //Колко метра нагоре се шчита за издигане
    float downHeightM=-2.0f; //Колко метра надолу се шчита за издигане
    float oldHeight = 0f; //Височина в началото на измерването.
    int previousCondition=0; //Състояние 0 -  нутрално, 1 качва се, -1 слиза
    int currentCondition=0; //Състояние 0 -  нутрално, 1 качва се, -1 слиза
    int conditionCounter=0; //Колко седи в това състояние - коунтер.
    int Counter1=-1; //Параметър за старт

    //Параметри за изменнение на ситуацията - стартиране на качване например
    //Ще ползвам общо качване, брой цикли changeProcessor();
    //float cChangeThreshold =0.15f; //Стойност, която се приема, че има движение.
    int cUpCounter=0; //Брои последователни измервания за нагоре
    int cDownCounter=0; //Брои последователни измервания надолу
    int cUpDownCounter=0; //Брои последователните изменения нагоре надолу
    int cEventCounter1=0;//Брои измененията
    boolean cUpActive=false; //Ако има активен процес за нагоре
    float cAllUpSum=0.0f;
    boolean cDownActive=false; //Ако има активен процес за нагоре
    boolean cStableActive=false; //Ако има активен процес за нагоре
    int cPreviousState=0; //Ако е 0 е стабилно, ако -1 е падал ако е 1 се е качвал
    //нови параметри
    int cAvgMeasures=5; //Колко стойности ще се вземат за измерването на показател
    float cAvgUpHeightChangeThreshold =.2f; //Колко метра/секунда нарастване се смята за изменение
    float cAvgDownHeightChangeThreshold =-0.2f; //Колко метра надолу ще се шчита за издигане
    movingAvgCalculator cAvgVerticalSpeedChange;









    private long mStartTime;
    private int seconds;
    private int minutes;
    private long millis;

    public EventProcessor(fileWriteRead cfileWrite, currentState CurrentState, Context that, fileRead efileRead) {
        eSoundPlayer = new SoundPlayer(that);
        counterGLO=0;
        this.CurrentState=CurrentState;
        this.dfileRead=efileRead;

        this.eState = eStarting;
        this.ePreviousState = eStarting;
        this.cfileWrite = cfileWrite;
        cAvgValuesCountv1=5;
        cAvgValuesCountv2=20;
        cAvgVerticalSppedCalculator1 = new movingAvgCalculator(cAvgValuesCountv1);
        cAvgVerticalSppedCalculator2 = new movingAvgCalculator(cAvgValuesCountv2);
        //калкулатор на средно качване за определен брой качвания
        cAvgVerticalSpeedChange = new movingAvgCalculator(cAvgMeasures); //Средно изменение

        startTimer();
    }

    public void pushValues(float verticalSpeed, float altitudeF) {

        eVerticalSpeedXCTold=eVerticalSpeedXCT;
        counterGLO++;

        if(!readFromFile) //ako четем данни от файл данните от уреда не се извличат
        {
            eVerticalSpeedXCT=verticalSpeed;
            eAltitudeXCT=altitudeF;
        } else              //четат се данни от записан файл
        {
            dfileRead.Read();
                if(dfileRead.getState()){
                eVerticalSpeedXCT=dfileRead.getVerticalSpeed();
                eAltitudeXCT=dfileRead.getAltitude();
            }

        }
        eAcceleration=(eVerticalSpeedXCT-eVerticalSpeedXCTold)/0.2f;
        eVerticalSpeedChangeOld=eVerticalSpeedChange;
        eVerticalSpeedChange=eVerticalSpeedXCTold-eVerticalSpeedXCT;



        cAvgVerticalSppedCalculator1.pushValue(eVerticalSpeedXCT);
        cAvgVerticalSppedCalculator2.pushValue(eVerticalSpeedXCT);
        cAvgVerticalSpeedChange.pushValue(eVerticalSpeedChange);

        processValues(); //това изчислява средните стойности
        updateTimer(); //ъпдейтва таймера за да може да изведе времето UI
        cStateNotify(); //записва състоянието и ъпдейтва показателите в currentState за да може да ползват за ъпдейтване на UI
        eventUpdate(); //подава данни за да се търси събитие
       //Manualiy disabled verticalSpeedChangeCounter(); //това ще брои изменения в вертикалната скорост и ще отборява мерки промяна
        zeroUpDownProcessor();
        changeProcessor();


    }


    // Установяване на сустояние
    private void changeProcessor() {

        boolean up = false;
        boolean down = false;
        boolean middle = false;

        if (eVerticalSpeedXCT > -0.9) {
            cEventCounter1++;

            if(cEventCounter1>75){

                switch (cPreviousState){
                    case 0: {cPreviousState=1;
                        break;}
                    case 1: {cPreviousState=0;
                        break;}
                    case -1: {cPreviousState=0;
                        break;}
                }
                cEventCounter1=0;

             }


            if (cAvgVerticalSpeedChange.getAvg() > cAvgUpHeightChangeThreshold) {
                up = true;
            }

            if (cAvgVerticalSpeedChange.getAvg() < cAvgDownHeightChangeThreshold) {
                down = true;
            }
            if ((cAvgVerticalSpeedChange.getAvg() < cAvgUpHeightChangeThreshold) && (cAvgVerticalSpeedChange.getAvg() > cAvgDownHeightChangeThreshold)) {
                middle = true;
            }

            if (up && (cPreviousState == 0 || cPreviousState == -1)) {
                eSoundPlayer.playUpDownChange(1);
                cPreviousState = 1;
                Log.i("UP PLAY", String.valueOf("---"+cAvgVerticalSpeedChange.getAvg()));
                cEventCounter1=0;
            }
            if (down && (cPreviousState == 0 || cPreviousState == 1)) {
                eSoundPlayer.playUpDownChange(2);
                cPreviousState = -1;
                Log.i("DOWN PLAY", String.valueOf("---"+cAvgVerticalSpeedChange.getAvg()));
                cEventCounter1=0;
            }
            if (middle && (cPreviousState == -1 || cPreviousState == 1)) {
                eSoundPlayer.playUpDownChange(0);
                cPreviousState = 0;
                Log.i("HORIZONTAL PLAY", String.valueOf("---"+cAvgVerticalSpeedChange.getAvg()));
                cEventCounter1=0;
            }
        }
    }


//Прочита какво е състоянието качване/слизане/стабилно
    private void zeroUpDownProcessor() {
        int counter;
        int sound;
        float difference;
        boolean climb = false;
        boolean dive = false;
        boolean horizontal = false;

        if(Counter1==-1){
            oldHeight=eAltitudeXCT;
            Counter1=0;
        }

        if(++Counter1==numberOfCycles) {
            difference = eAltitudeXCT - oldHeight;//Разлика с предишшния каунтер
            oldHeight=eAltitudeXCT;
            Counter1=0;
        //    Log.i("Differenc", String.valueOf(difference) + "UPHeight"+String.valueOf(upHeightM));
            if(difference>upHeightM)
            { //Качване


                previousCondition=currentCondition;
                currentCondition=1;
                if(currentCondition==previousCondition)
                {
                    conditionCounter++;
                } else {
                    conditionCounter=0;
                }
                if(conditionCounter<3){
                    eSoundPlayer.playUpDown(1);
                }

            }else {
                if(difference<downHeightM) //Слизане
                {


                    if(currentCondition==previousCondition)
                    {
                        conditionCounter++;
                    } else {
                        conditionCounter=0;
                    }
                    if(conditionCounter<3){
                        eSoundPlayer.playUpDown(-1);
                    }
                } else {           //Междинно
                   sound=0;
                 //   eSoundPlayer.playUpDown(sound);
                    Log.i("Differenc", String.valueOf(difference) + "UPHeight"+ String.valueOf(upHeightM));
                    if(currentCondition==previousCondition)
                    {
                        conditionCounter++;
                    } else {
                        conditionCounter=0;
                    }
                    if(conditionCounter<3){
                        eSoundPlayer.playUpDown(0);
                    }
                }
            }
        }

    }

    private void verticalSpeedChangeCounter() {
        float testValue;
        testValue=cAvgVerticalSeed1;
        float verticalSpeedCountValueUP=0.3f;
        float verticalSpeedCountValueDown=0.6f;

        if(testValue>verticalSpeedCountValueUP*(currentVerticalSpeedCount+1)){
            if(testValue-verticalSpeedCountValueUP*(currentVerticalSpeedCount+1)>verticalSpeedCountValueUP*0.2){
                currentVerticalSpeedCount++;
                counterTimesAboveVerticalSpeed=0;

                    eSoundPlayer.playNow(currentVerticalSpeedCount,1);

            } else{
                if(counterTimesAboveVerticalSpeed++>3){
                    currentVerticalSpeedCount++;
                    counterTimesAboveVerticalSpeed=0;


                        eSoundPlayer.playNow(currentVerticalSpeedCount,1);

                }
            }

            return;
        }
        if(testValue<verticalSpeedCountValueDown*(currentVerticalSpeedCount-1)){

            if(verticalSpeedCountValueDown*(currentVerticalSpeedCount-1)-testValue>verticalSpeedCountValueDown*0.2)
            {
                currentVerticalSpeedCount--;
                counterTimesAboveVerticalSpeed=0;

                    eSoundPlayer.playNow(currentVerticalSpeedCount,-1);

            } else{
                if(counterTimesAboveVerticalSpeed++>3){
                    currentVerticalSpeedCount--;
                    counterTimesAboveVerticalSpeed=0;


                        eSoundPlayer.playNow(currentVerticalSpeedCount,-1);

                }
            }

            return;

        }



    }

    private void eventUpdate() {
      /*  if(counter++>10){

            switch(eState){
                case eStable: {
                    if(Math.abs(eVerticalSpeedXCT-cAvgVerticalSeed1)>cDispersion1old&&(Math.abs(eVerticalSpeedXCT-cAvgVerticalSeed1))>0.1){

                        if (eVerticalSpeedXCT - cAvgVerticalSeed1 > 0) {
                            eSoundPlayer.playNow(1,1);
                            if (eAttention++ == 0) attention(1);
                        } else {
                            eSoundPlayer.playNow(-1,-1);
                            if(eAttention--==0) attention(-1);
                        }

                        //if()






                    }


                }


            }


        }else{
            eState=eStable;
            currentEventStart=System.currentTimeMillis()-mStartTime;
            eventCurrentAltitude=eventStartAltitude=eAltitudeXCT;
            eventCurrentVertical=eventStartVertical=cAvgVerticalSeed1;
        }
*/
    }

    private void attention(int i) {
        //eSoundPlayer.playNow(i);



    }

    private void updateTimer() {
        if(!readFromFile){
        millis = System.currentTimeMillis() - mStartTime;
        seconds = (int) (millis / 1000);
        minutes = seconds / 60;
        seconds = seconds % 60;}
        else {
            seconds=dfileRead.getSeconds();
            minutes=dfileRead.getMinutes();

        }

    }
    public void startTimer(){
        mStartTime= System.currentTimeMillis();
    }

    private void cStateNotify() {

          String cData=millis+","+minutes+","+seconds+","+ eVerticalSpeedXCT+ "," + cAvgVerticalSeed1 + ","
                    +cAvgVerticalSeed2+","+eAltitudeXCT+","+cDispersion2+","+cDispersion1;
            cfileWrite.fWrite(cData);

        CurrentState.setcAltitude(eAltitudeXCT);
        CurrentState.setcRawVerticalSpeed(eVerticalSpeedXCT);
        CurrentState.setcAvgVerticalSpeedChange(cAvgVerticalSpeedChange.getAvg());
        CurrentState.setcAvgVerticalSeed1(cAvgVerticalSeed1);
        CurrentState.setcAvgVerticalSeed2(cAvgVerticalSeed2);
        CurrentState.setcDispersion1(cDispersion1);
        CurrentState.setcDispersion2(cDispersion2);
        CurrentState.setMinutes(minutes);
        CurrentState.setSeconds(seconds);

    }

    private void processValues(){
        cDispersion1old=cDispersion1;
        cDispersion1 =cAvgVerticalSppedCalculator1.getStddev();
        cDispersion2old=cDispersion2;
        cDispersion2 =cAvgVerticalSppedCalculator2.getStddev();
        cAvgVerticalSeed1=cAvgVerticalSppedCalculator1.getAvg();
        cAvgVerticalSeed2=cAvgVerticalSppedCalculator2.getAvg();

    }
}
