package com.soolva.flymeditation.someClasses;

public interface MainActivityContract {
    /** Represents the View in MVP. */
    interface View {
        void showElevation (Float elevation);
        void showVerticalSpeed(Float vertical);
        void showUpStep(Float upStep);
        void showDownStep(Float downStep);
        public float getUpStepInput();
        public float getDownStepInput();


    }
    /** Represents the Presenter in MVP. */
    interface Presenter {
        void loadElevation();
        void loadVerticalSpeed();
        void runThis();
        void loadUpStepValue();
        void loadDownStepValue();

        void enterParameters();
    }


}

