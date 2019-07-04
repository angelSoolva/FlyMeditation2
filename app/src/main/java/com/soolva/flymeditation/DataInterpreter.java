package com.soolva.flymeditation;

public class DataInterpreter {
    Global_Data_Class GlobalData;

    Float OldAltitude;
    Float VerticalStepUp = 0.2F; //Step on which a signal will be created
    Float VerticalStepDown = 0.2F; //Step on which a signal will be created
    Boolean starting = true;
    Float CurrentAltitudeBase; //Altitude to compare height
    SoundPlayer SP;

    DataInterpreter(Global_Data_Class GD, SoundPlayer sp){
        GlobalData = GD;
        SP = sp;

    }

    public void pushValues(float verticalSpeed, float altitudeF) {
        VerticalStepUp=GlobalData.getUpStepValue();
        VerticalStepDown=GlobalData.getDownStepValue();
        if(starting) {
           CurrentAltitudeBase = Float.valueOf(Math.round(altitudeF));
           starting=false;
           return;
        } else {
            if(altitudeF- CurrentAltitudeBase > VerticalStepUp) //UP step
            {
                SP.playUP();
                CurrentAltitudeBase= CurrentAltitudeBase + VerticalStepUp; //New Base
            }
            if(altitudeF - CurrentAltitudeBase < -VerticalStepDown) //Down step
            {
                //Bang
                SP.playDOWN();
                CurrentAltitudeBase= CurrentAltitudeBase - VerticalStepDown; //New Base
            }

        }


    }
}
