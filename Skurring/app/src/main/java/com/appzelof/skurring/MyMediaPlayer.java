package com.appzelof.skurring;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;

import com.appzelof.skurring.Interfaces.StreamInfoUpdate;
import com.vodyasov.amr.AudiostreamMetadataManager;
import com.vodyasov.amr.OnNewMetadataListener;
import com.vodyasov.amr.UserAgent;

import java.util.ArrayList;
import java.util.List;

public class MyMediaPlayer implements MediaPlayer.OnPreparedListener {

    public static MyMediaPlayer INSTANCE;
    private MediaPlayer mediaPlayer;
    private String chosenRadioChannel;
    public StreamInfoUpdate streamInfoUpdate;
    private ArrayList<String> metadataList;

    public void initAndPrepareAndPlay(String url) {
        mediaPlayer = new MediaPlayer();
        metadataList = new ArrayList<>();
        this.chosenRadioChannel = url;
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnPreparedListener(this);

        try {
            mediaPlayer.setDataSource(url);
        } catch (Exception e) {
            System.out.println("Media player error: " + e.getMessage());
        }

        mediaPlayer.prepareAsync();

    }

    private void startMetadataRecording() {
        AudiostreamMetadataManager.getInstance().setUri(Uri.parse(chosenRadioChannel))
                .setOnNewMetadataListener(listener)
                .setUserAgent(UserAgent.WINDOWS_MEDIA_PLAYER)
                .start();
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void stopMediaPlayerAndMetadataRecording() {
        this.mediaPlayer.stop();
        this.mediaPlayer.release();
        this.mediaPlayer = null;
        AudiostreamMetadataManager.getInstance().stop();
    }

    private OnNewMetadataListener listener = new OnNewMetadataListener() {
        @Override
        public void onNewHeaders(String stringUri, List<String> name, List<String> desc, List<String> br, List<String> genre, List<String> info) {
            for (String theName: name) {
                System.out.println("Name: " + theName);
            }
        }

        @Override
        public void onNewStreamTitle(String stringUri, String streamTitle) {
            System.out.println("TITLE: " + streamTitle);
            extractData(streamTitle);
        }
    };

    private void extractData(String info) {
        this.metadataList.clear();
        String metaInfo = info;
        if (info.contains("-")) {
            metaInfo = info.split("-")[0];
        } else if (info.contains("&")) {
            metaInfo = info.split("&")[0];
        } else {
            boolean foundEqual = false;
            for (Character c : info.toCharArray()) {
                if (c == '=') {
                    foundEqual = true;
                    break;
                }
            }
            if (foundEqual) {
                metaInfo = "";
            }
        }

        streamInfoUpdate.getInfo(metaInfo);
    }


    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mediaPlayer.start();
        this.startMetadataRecording();
    }
}
