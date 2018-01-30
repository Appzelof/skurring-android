package com.appzelof.skurring.mediaPlayer;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;

import java.io.IOException;
import java.net.URL;

/**
 * Created by daniel on 23/11/2017.
 */

public class SoundPlayer {


    private MediaPlayer mediaPlayer;

    public SoundPlayer(){

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);


    }




    public void play(Context context, Uri uri){

        if (!mediaPlayer.isPlaying()) {

            try {
                mediaPlayer.setDataSource(context, uri);
                mediaPlayer.prepareAsync();

                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mediaPlayer.start();
                    }
                });

            } catch (IOException e) {
                System.out.println("Could not play" + e.getMessage());

            }

        } else {
            mediaPlayer.stop();
            mediaPlayer.release();
        }


    }

    public boolean stop() {



            mediaPlayer.stop();


        return true;
    }

}
