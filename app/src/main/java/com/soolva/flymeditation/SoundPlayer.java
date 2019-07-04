package com.soolva.flymeditation;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.Handler;
import android.util.Log;

/**
 * Created by angel on 14.4.2017 г..
 */

public class SoundPlayer {

    /**
     * Created by angel on 6.6.2016 г..
     */

        Handler timerHandler = new Handler();
        final MediaPlayer mediaPlayer = new MediaPlayer();
        //Sound pool objects
        SoundPool sp1;
        SoundPool sp2;
        long mStartTime;
        private int soundID;
        private int soundID2;
        boolean plays = false, loaded = false;
        float actVolume, maxVolume, volume;
        AudioManager audioManager;
        int counter;
        private Handler mHandler = new Handler();
        float old_velocity=0f;
        float new_velocity;
        long timer_millis;
        long start_time= System.currentTimeMillis();

        boolean UP=false, DOWN=false, HORIZONTAL=true, STABLE=true;
        boolean old_UP=false, old_DOWN=false, OLD_HORIZONTAL = true;
        boolean start=true;
        boolean sound_played=false;
        float threshold=0.2f;
        float threshold1 =0.2f;
        float threshold2 =0.2f;


        //sounds
       int zero_up, zero_down;
    int piano1, piano2, piano3, piano4;
    int piano5, piano6, piano7, piano8;
    int guitar1, guitar2,guitar3,guitar4;
    int guitar5, guitar6,guitar7,guitar8;
    int trombone1, trombone2,trombone3,trombone4;
    int trombone5, trombone6,trombone7,trombone8;
    int dbass1, dbass2, dbass3, dbass4;
    int dbass5, dbass6, dbass7, dbass8;
    int flate;
    //sounds UP Down Major
    int piano_up;
    int piano_down;
    int piano_horizontal;
    //sounds for vertical speed change
    int up_change_signal;
    int down_change_signal;
    int stable_signal;





    int pianoUP, pianoUP2, pianoUP3, pianoUP4;
        int celesteUP, celesteUP2, celesteUP3, celesteUP4;
        int saxDOWN, saxDOWN2, saxDOWN3, saxDOWN4;
        int clarinetDOWN, clarinetDOWN2, clarinetDOWN3, clarinetDOWN4;

        int updrum, downdrum,trompet,bass, violine;
       // int guitar1, guitar2, guitar3, guitar4, guitar5;

    public SoundPlayer(Context that) {



            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                AudioAttributes audioAttrib = new AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_GAME)
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .build();
                sp1 = new SoundPool.Builder().setAudioAttributes(audioAttrib).setMaxStreams(20).build();
                sp2 = new SoundPool.Builder().setAudioAttributes(audioAttrib).setMaxStreams(20).build();


            }
            else {

                sp1 = new SoundPool(20, AudioManager.STREAM_MUSIC, 0);
                sp2 = new SoundPool(20, AudioManager.STREAM_MUSIC, 0);

            }

            audioManager = (AudioManager) that.getSystemService(Context.AUDIO_SERVICE);
            actVolume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            maxVolume = (float) audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            //volume = actVolume / maxVolume;
        volume=1;
            //Hardware buttons setting to adjust the media sound
            //that.setVolumeControlStream(AudioManager.STREAM_MUSIC);
            // the counter will help us recognize the stream id of the sound played  now
            counter = 0;

// Loading the sounds

        zero_up=sp1.load(that, R.raw.zero_up,1);
        zero_down=sp1.load(that, R.raw.zero_down,1);

        piano1=sp1.load(that, R.raw.piano1_c3_6_n,1);
        piano2=sp1.load(that, R.raw.piano2_c4_2_n,1);
        piano3=sp1.load(that, R.raw.piano3_c4_4_n,1);
        piano4=sp1.load(that, R.raw.piano4_c4_7_n,1);
        piano5=sp1.load(that, R.raw.piano5_c5_3_n,1);
        piano6=sp1.load(that, R.raw.piano6_c5_5_n,1);
        piano7=sp1.load(that, R.raw.piano7_c6_1_n,1);
        piano8=sp1.load(that, R.raw.piano8_c6_3_n,1);

        guitar1=sp1.load(that, R.raw.guitar1_c6_7_n,1);
        guitar2=sp1.load(that, R.raw.guitar2_c6_3_n,1);
        guitar3=sp1.load(that, R.raw.guitar3_c5_7_n,1);
        guitar4=sp1.load(that, R.raw.guitar4_c5_5_n,1);
        guitar5=sp1.load(that,R.raw.guitar5_c5_2_n,1);
        guitar6=sp1.load(that,R.raw.guitar6_c4_6_n,1);
        guitar7=sp1.load(that,R.raw.guitar7_c4_3_n,1);
        guitar8=sp1.load(that,R.raw.guitar8_c3_5_n,1);

        trombone1=sp1.load(that, R.raw.trombone1_c3_6_n,1);
        trombone2=sp1.load(that, R.raw.trombone2_c4_2_n,1);
        trombone3=sp1.load(that, R.raw.trombone3_c4_4_n,1);
        trombone4=sp1.load(that, R.raw.trombone4_c4_7_n,1);
        trombone5=sp1.load(that, R.raw.trombone5_c5_3_n,1);
        trombone6=sp1.load(that, R.raw.trombone6_c5_5_n,1);
        trombone7=sp1.load(that, R.raw.trombone7_c6_1_n,1);
        trombone8=sp1.load(that, R.raw.trombone8_c6_3_n,1);

        dbass1=sp1.load(that, R.raw.dbass1_c6_1_n,1);
        dbass2=sp1.load(that, R.raw.dbass2_c5_3_n,1);
        dbass3=sp1.load(that, R.raw.dbass3_c5_1_n,1);
        dbass4=sp1.load(that, R.raw.dbass4_c4_6_n,1);
        dbass5=sp1.load(that, R.raw.dbass5_c4_4_n,1);
        dbass6=sp1.load(that, R.raw.dbass6_c4_2_n,1);
        dbass7=sp1.load(that, R.raw.dbass7_c3_7_n,1);
        dbass8=sp1.load(that, R.raw.dbass8_c3_5_n,1);

        flate=sp1.load(that, R.raw.flate,1);

        //used for the moment sounds
        //state sounds
        piano_up=sp1.load(that, R.raw.piano_up_c5_c6,1);
        piano_horizontal=sp1.load(that, R.raw.piano_horizontal_c3_3_c3_3,1);
        piano_down=sp1.load(that, R.raw.piano_down,1);
        //change sounds
        down_change_signal=sp1.load(that, R.raw.change_signal_down,1);
        up_change_signal=sp1.load(that, R.raw.change_signal_up,1);
        stable_signal=sp1.load(that, R.raw.change_signal_no,1);








        /*pianoUP = sp1.load(that,R.raw.piano_up,1);
            pianoUP2 = sp1.load(that,R.raw.piano_up_2,1);
            pianoUP3 = sp1.load(that,R.raw.piano_up_3,1);
            pianoUP4 = sp1.load(that,R.raw.piano_up_4,1);

            celesteUP = sp1.load(that,R.raw.celeste_up,1);
            celesteUP2 = sp1.load(that,R.raw.celeste_up_2,1);
            celesteUP3 = sp1.load(that,R.raw.celeste_up_3,1);
            celesteUP4 = sp1.load(that,R.raw.celeste_up_4,1);

            saxDOWN = sp1.load(that,R.raw.sax_down,1);
            saxDOWN2 = sp1.load(that,R.raw.sax_down_2,1);
            saxDOWN3 = sp1.load(that,R.raw.sax_down_3,1);
            saxDOWN4 = sp1.load(that,R.raw.sax_down_4,1);

            clarinetDOWN = sp1.load(that,R.raw.clarinet_down,1);
            clarinetDOWN2 = sp1.load(that,R.raw.clarinet_down_2,1);
            clarinetDOWN3 = sp1.load(that,R.raw.clarinet_down_3,1);
            clarinetDOWN4 = sp1.load(that,R.raw.clarinet_down_4,1);


         piano5 = sp1.load(that,R.raw.piano5,1);

        guitar1 = sp1.load(that,R.raw.guitar10ds4,1);
        guitar2 = sp1.load(that,R.raw.guitar9e2,1);
        guitar3 = sp1.load(that,R.raw.guitar8e4,1);
        guitar4 = sp1.load(that,R.raw.guitar7f2,1);
        guitar5 = sp1.load(that,R.raw.guitar6f4,1);

        updrum = sp1.load(that,R.raw.updrum,1);
        downdrum = sp1.load(that,R.raw.downdrum,1);
        trompet= sp1.load(that,R.raw.trompet01,1);
        bass=sp1.load(that,R.raw.bass_10,1);
        violine=sp1.load(that,R.raw.upvioline,1);
        */





        }

        public void setThresholdOver0(float threshold) {
            this.threshold1 = threshold;
        }
        public void setThresholdBelow0(float threshold) {
            this.threshold2 = threshold;
        }
        //Gets new vertical velocity
        //The new value will be processed and the sound playing function will be envocked
    //One step UP SOUND - step is settable Height - 1 m for example.
    public void playUP(){
        sp1.play(stable_signal, volume, volume, 1, 0, 1f);
    }
    //One step DOWN SOUND - step is Height - 1 m for example.
    public void playDOWN(){
        sp1.play(down_change_signal, volume, volume, 1, 0, 1f);
    }

    public void playNow(int Sound,int upDown){

          //  sp1.play(clarinetDOWN4, volume, volume, 1, 0, 1f);


        if(upDown==1) {

            switch (Sound) {
                case 0:
                    sp1.play(zero_up, volume, volume, 1, 0, 1f);
                case 1:
                    sp1.play(piano1, volume, volume, 1, 0, 1f);
                    break;
                case 2:
                    sp1.play(piano2, volume, volume, 1, 0, 1f);
                    break;
                case 3:
                    sp1.play(piano3, volume, volume, 1, 0, 1f);
                    break;
                case 4:
                    sp1.play(piano4, volume, volume, 1, 0, 1f);
                    break;
                case 5:
                    sp1.play(piano5, volume, volume, 1, 0, 1f);
                    break;
                case 6:
                    sp1.play(piano6, volume, volume, 1, 0, 1f);
                    break;
                case 7:
                    sp1.play(piano7, volume, volume, 1, 0, 1f);
                    break;
                case 8:
                    sp1.play(piano8, volume, volume, 1, 0, 1f);
                    break;

                case -1:
                    sp1.play(dbass1, volume, volume, 1, 0, 1f);
                    break;
                case -2:
                    sp1.play(dbass2, volume, volume, 1, 0, 1f);
                    break;
                case -3:
                    sp1.play(dbass3, volume, volume, 1, 0, 1f);
                    break;
                case -4:
                    sp1.play(dbass4, volume, volume, 1, 0, 1f);
                    break;

            }
        }
        if(upDown==-1) {

            switch (Sound) {
                case 0:
                    sp1.play(zero_up, volume, volume, 1, 0, 1f);

                case 1:
                    sp1.play(guitar1, volume, volume, 1, 0, 1f);
                    break;
                case 2:
                    sp1.play(guitar2, volume, volume, 1, 0, 1f);
                    break;
                case 3:
                    sp1.play(guitar3, volume, volume, 1, 0, 1f);
                    break;
                case 4:
                    sp1.play(guitar4, volume, volume, 1, 0, 1f);
                    break;

                case 5:
                    sp1.play(guitar5, volume, volume, 1, 0, 1f);
                    break;
                case 6:
                    sp1.play(guitar6, volume, volume, 1, 0, 1f);
                    break;
                case 7:
                    sp1.play(guitar7, volume, volume, 1, 0, 1f);
                    break;
                case 8:
                    sp1.play(guitar8, volume, volume, 1, 0, 1f);
                    break;

                case -1:
                    sp1.play(flate, volume, volume, 1, 0, 1f);
                    break;
                case -2:
                    sp1.play(flate, volume, volume, 1, 0, 1f);
                    break;
                case -3:
                    sp1.play(flate, volume, volume, 1, 0, 1f);
                    break;
                case -4:
                    sp1.play(flate, volume, volume, 1, 0, 1f);
                    break;

            }
        }


    }


    public void pushKalmanVelocity(Float velocity) {


            if (start) {
                play_sound(false,false,velocity);
                sound_played=true;
                old_velocity=velocity;
                start=false;
                start_time= System.currentTimeMillis();
            }


            if(velocity>=0){threshold=threshold1;}else{threshold=threshold2;}

            if((Math.abs(velocity-old_velocity)> threshold)) {
                int count = Math.round((velocity - old_velocity )/ threshold);

                play_difference( count, velocity);
                old_velocity = velocity - (velocity - old_velocity - threshold*count);


           /* if((velocity-old_velocity)>0){
                if(UP){
                play_sound(false,false, velocity);
            } else {
                    play_sound(true,false,velocity);
                    old_UP=UP;
                    UP=true;
                    old_DOWN=DOWN;
                    DOWN=false;}
                }
             else
            {
                if(DOWN){
                    play_sound(false,false, velocity);
                } else {
                    play_sound(false,true, velocity);
                    old_UP=UP;
                    UP=false;
                    old_DOWN=DOWN;
                    DOWN=true;
                }
            } */

                start_time = System.currentTimeMillis();

            }

            timer_millis= System.currentTimeMillis() - start_time;

       /* if(timer_millis>5000)
        {
            play_sound(false,false,velocity);
            start_time=System.currentTimeMillis();
        }*/
            sound_played=false;

        }

        public void play_difference(int count, float velocity) {
            if(count>3) {count=4;}
            if(count<-3){count=-4;}

            if(velocity >= 0) {
                switch (count) {
                    case 1:
                        sp1.play(celesteUP, volume, volume, 1, 0, 1f);
                        break;
                    case 2:
                        sp1.play(celesteUP2, volume, volume, 1, 0, 1f);


                    case 3:
                        sp1.play(celesteUP3, volume, volume, 1, 0, 1f);
                        break;

                    case 4:
                        sp1.play(celesteUP4, volume, volume, 1, 0, 1f);
                        break;

                    case -1:
                        sp1.play(clarinetDOWN, volume, volume, 1, 0, 1f);
                        break;
                    case -2:
                        sp1.play(clarinetDOWN2, volume, volume, 1, 0, 1f);
                        break;
                    case -3:
                        sp1.play(clarinetDOWN3, volume, volume, 1, 0, 1f);
                    case -4:
                        sp1.play(clarinetDOWN4, volume, volume, 1, 0, 1f);
                        break;

                }
            } else
            //in case the velocity is negative the sound marking positive and negative change will be different
            {
                switch (count) {
                    case 1:
                        sp1.play(pianoUP, volume, volume, 1, 0, 1f);
                        break;
                    case 2:
                        sp1.play(pianoUP2, volume, volume, 1, 0, 1f);
                        break;
                    case 3:
                        sp1.play(pianoUP3, volume, volume, 1, 0, 1f);
                        break;
                    case 4:
                        sp1.play(pianoUP4, volume, volume, 1, 0, 1f);
                        break;

                    case -1:
                        sp1.play(saxDOWN, volume, volume, 1, 0, 1f);
                        break;
                    case -2:
                        sp1.play(saxDOWN2, volume, volume, 1, 0, 1f);
                        break;
                    case -3:
                        sp1.play(saxDOWN3, volume, volume, 1, 0, 1f);
                        break;
                    case -4:
                        sp1.play(saxDOWN4, volume, volume, 1, 0, 1f);
                        break;
                }

            }


        }


        public void play_sound(boolean up,boolean down, float velocity) {
/*
            if (up) {
                sp1.play(updrum, volume, volume, 1, 0, 1f);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (down) {
                sp1.play(downdrum, volume, volume, 1, 0, 1f);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if ((velocity >= -0.1) && (velocity < 1.0)) {
                if ((velocity >= 0.0) && (velocity < 0.1)) {
                    sp1.play(trompet, volume, volume, 1, 0, 1f);
                }
                if ((velocity >= -0.1) && (velocity < 0.0)) {
                    sp1.play(bass, volume, volume, 1, 0, 1f);
                }
            }
            if ((velocity >= 0.1) && (velocity < 1.0)) {
                if ((velocity >= 0.1) && (velocity < 0.2)) {
                    sp1.play(pianoUP, volume, volume, 1, 0, 1f);
                }
                if ((velocity >= 0.2) && (velocity < 0.4)) {
                    sp1.play(pianoUP3, volume, volume, 1, 0, 1f);
                }
                if ((velocity >= 0.4) && (velocity < 0.6)) {
                    sp1.play(pianoUP4, volume, volume, 1, 0, 1f);
                }
                if ((velocity >= 0.6) && (velocity < 0.8)) {
                    sp1.play(pianoUP2, volume, volume, 1, 0, 1f);
                }
                if ((velocity >= 0.8) && (velocity < 1.0)) {
                    sp1.play(piano5, volume, volume, 1, 0, 1f);
                }
            }
            if ((velocity <= -0.1) && (velocity > -1.0)) {
                if ((velocity <= -0.1) && (velocity > -0.2)) {
                    sp1.play(guitar1, volume, volume, 1, 0, 1f);
                }
                if ((velocity <= -0.2) && (velocity > -0.4)) {
                    sp1.play(guitar2, volume, volume, 1, 0, 1f);
                }
                if ((velocity <= -0.4) && (velocity > -0.6)) {
                    sp1.play(guitar3, volume, volume, 1, 0, 1f);
                }
                if ((velocity <= -0.6) && (velocity > -0.8)) {
                    sp1.play(guitar4, volume, volume, 1, 0, 1f);
                }
                if ((velocity <= -0.8) && (velocity > -1.0)) {
                    sp1.play(guitar5, volume, volume, 1, 0, 1f);
                }


            }

        */}


    public void playUpDown(int i) {
        switch (i) {
            case 0:{
                sp1.play(piano_horizontal, volume/2, volume/2, 1, 0, 1f);

                Log.i("Diff PianoHorizontal", "Horizontal");
                break;

            }

            case 1:{
                sp1.play(piano_up, volume/2, volume/2, 1, 0, 1f);
                break;}
            case -1:{
                sp1.play(piano_down, volume/2, volume/2, 1, 0, 1f);
                break;
    }
    }
    }

    public void playUpDownChange(int i) {
        switch (i) {
            case 0:{
                sp1.play(stable_signal, volume, volume, 1, 0, 1f);
                break;

            }

            case 1:{
                sp1.play(up_change_signal, volume, volume, 1, 0, 1f);
                break;}

            case 2:{
                sp1.play(down_change_signal, volume, volume, 1, 0, 1f);
                break;
            }
        }
    }
}


