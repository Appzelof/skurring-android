package com.appzelof.skurring;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.TimedMetaData;
import android.net.Uri;

import com.appzelof.skurring.Interfaces.StreamInfoUpdate;
import com.appzelof.skurring.model.PotentialMetadataJSONObject;
import com.vodyasov.amr.AudiostreamMetadataManager;
import com.vodyasov.amr.AudiostreamMetadataRetriever;
import com.vodyasov.amr.OnNewMetadataListener;
import com.vodyasov.amr.UserAgent;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MyMediaPlayer implements MediaPlayer.OnPreparedListener {

    private MediaPlayer mediaPlayer;
    private String chosenRadioChannel;
    public StreamInfoUpdate streamInfoUpdate;

    public MyMediaPlayer(){}

    public void initAndPrepareAndPlay(String url) {
        mediaPlayer = new MediaPlayer();
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
            System.out.println("INFO: " + info.toString());
        }

        @Override
        public void onNewStreamTitle(String stringUri, String streamTitle) {
            System.out.println("TITLE: " + streamTitle);
            System.out.println("URI : " + stringUri);
            extractData(streamTitle);
        }
    };

    private void extractData(String info) {
        String[] metaInfo = {"1", "2"};
        boolean isJson = false;
        boolean pureInfo = false;
        for (Character c: info.toCharArray()) {
            if (c == '=') {
                isJson = true;
                metaInfo = info.split("=");
                break;
            }
        }
        if (!isJson) {
            if (info.contains(" - ")) {
                metaInfo = info.split(" - ");
            } else if (info.contains("-")) {
                metaInfo = info.split("-");
            } else {
                if (info.contains(" & ")) {
                    metaInfo = info.split(" & ");
                } else if (info.contains("&")) {
                    metaInfo = info.split("&");
                } else {
                    if (info.contains("med")) {
                        metaInfo = info.split("med");
                    } else if (info.contains(" med ")) {
                        metaInfo = info.split(" med ");
                    } else {
                        pureInfo = true;
                    }
                }
            }
        }


        if (metaInfo.length != 0 && metaInfo.length > 1) {
            if (pureInfo) {
                streamInfoUpdate.getInfo(info, "", null);
            } else {
                System.out.println("ZERO: " + metaInfo[0]);
                System.out.println("ONE: " + metaInfo[1]);
                if (isJson) {
                    System.out.println(metaInfo[1]);
                    JSONObject extractedJSON = extractJSON(metaInfo[1]);
                    if (extractedJSON != null) {
                        PotentialMetadataJSONObject potentialMetadataJSONObject = new PotentialMetadataJSONObject(extractedJSON);
                        streamInfoUpdate.getInfo("", "", potentialMetadataJSONObject);
                    } else {
                        if (metaInfo[0].contains("StreamUrl")) {
                            metaInfo[0] = metaInfo[0].replace("StreamUrl", "");
                        }
                        streamInfoUpdate.getInfo(metaInfo[0], "", null);
                    }
                } else {
                    streamInfoUpdate.getInfo(metaInfo[0], metaInfo[1],  null);
                }
            }
        } else {
            streamInfoUpdate.getInfo("", "", null);
        }
    }

    private JSONObject extractJSON(String metaInfo) {
        try {
            return new JSONObject(metaInfo);
        } catch (Exception e) {
            System.out.println("JSON ERR: " + e.getMessage());
            return null;
        }

    }


    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mediaPlayer.start();
        this.startMetadataRecording();
    }

}
