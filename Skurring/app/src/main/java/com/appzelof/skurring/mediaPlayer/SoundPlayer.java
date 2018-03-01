package com.appzelof.skurring.mediaPlayer;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.TimedMetaData;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.widget.MediaController;

import com.appzelof.skurring.Interface.LiveData;

import java.io.IOException;
import java.net.URL;

/**
 * Created by daniel on 23/11/2017.
 */

public class SoundPlayer implements MediaPlayer.OnTimedMetaDataAvailableListener, AudioManager.OnAudioFocusChangeListener  {

    private MediaPlayer mediaPlayer;
    public LiveData liveData;

    public SoundPlayer(){
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnTimedMetaDataAvailableListener(this);
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


    public void stop() {
        if (mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }

    }

    @Override
    public void onTimedMetaDataAvailable(MediaPlayer mp, TimedMetaData data) {
        this.liveData.getLiveDataString(data.toString());

    }

    @Override
    public void onAudioFocusChange(int focusChange) {

        switch (focusChange) {
            case AudioManager.AUDIOFOCUS_GAIN:
                if (mediaPlayer != null) {
                    mediaPlayer.start();
                    break;
                }
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                mediaPlayer.release();
                break;
        }

    }

}
