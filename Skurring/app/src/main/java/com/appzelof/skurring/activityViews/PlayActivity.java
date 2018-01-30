package com.appzelof.skurring.activityViews;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;

import com.appzelof.skurring.R;
import com.appzelof.skurring.mediaPlayer.SoundPlayer;

import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URI;

public class PlayActivity extends AppCompatActivity implements View.OnClickListener{

    private View playView;
    private SoundPlayer soundPlayer;
    private ImageView imageView;
    private TextView textView;
    private String radioURL;
    private String radioName;
    private int radioImage;
    private Uri urlStream;


    @Override
    public void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        initializeData();


    }

    private void initializeData() {


        soundPlayer = new SoundPlayer();
        imageView = (ImageView) findViewById(R.id.my_play_image);
        textView = (TextView) findViewById(R.id.my_radio_play_name);
        playView = (View) findViewById(R.id.playView);

        textView.setText(getRadioName());
        imageView.setImageResource(getRadioImage());
        urlStream = Uri.parse(getRadioURL());
        soundPlayer.play(getApplicationContext(), urlStream);



        playView.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.playView:

                if (soundPlayer.stop() == true) {
                    soundPlayer.stop();
                    goBack();
                }
                break;
        }

    }

    private int getRadioImage(){
        Bundle extra = getIntent().getExtras();

        if (extra != null){
            radioImage = extra.getInt("radioImage");
        }


        return radioImage;
    }

    private String getRadioURL(){
        Bundle extra = getIntent().getExtras();

        if (extra != null){
            radioURL = extra.getString("radioURL");
            System.out.println(radioURL);
        }

        return radioURL;
    }

    private String getRadioName(){
        Bundle extra = getIntent().getExtras();

        if (extra != null){
            radioName = extra.getString("radioName");
        }

        return radioName;
    }


    private void goBack(){
        finish();
    }


}
