package com.soolva.flymeditation.someClasses;

import com.soolva.flymeditation.Global_Data_Class;
import com.soolva.flymeditation.MainActivity;

public class MainActivityPresenter implements MainActivityContract.Presenter {
    Boolean threadRun=false;


    //private Person person;
    private MainActivityContract.View view;
    final Global_Data_Class globalVariable;


    public MainActivityPresenter(MainActivityContract.View view, Global_Data_Class glv) {
         this.view = view; //MainActivity
         globalVariable = glv; //Program class for data exchange
    }
    @Override
    public void runThis (){
        loadUpStepValue();
        loadDownStepValue();
        if(!threadRun) {
    Thread thread = new Thread() {
        @Override
        public void run() {
            try {
                while(true) {
                    sleep(300);
                    loadElevation();
                    loadVerticalSpeed();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

       thread.start();
        threadRun =true;
        }
    }



    //Get Altitude and vertical speed
    @Override
    public void loadElevation(){

        view.showElevation(globalVariable.getAltitude());

    }
    @Override
    public void loadVerticalSpeed(){
        view.showVerticalSpeed(globalVariable.getVerticalSpeed());
    }

    @Override
    public void loadUpStepValue(){
        view.showUpStep(globalVariable.getUpStepValue());
    }
    @Override
    public void loadDownStepValue(){
        view.showDownStep(globalVariable.getDownStepValue());
    }
    @Override
    public void enterParameters() {
        Float upStep;
        Float downStep;
        upStep=view.getUpStepInput();
        downStep = view.getDownStepInput();
        if(!upStep.isNaN()){
            globalVariable.setUpStepValue(upStep);
            loadUpStepValue();
        }
        if(!downStep.isNaN()){
            globalVariable.setDownStepValue(downStep);
            loadDownStepValue();
        }

    }


}
