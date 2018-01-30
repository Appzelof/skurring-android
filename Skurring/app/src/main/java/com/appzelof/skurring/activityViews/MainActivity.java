package com.appzelof.skurring.activityViews;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import com.appzelof.skurring.R;
import com.google.firebase.analytics.FirebaseAnalytics;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener{

    private ImageButton btn;
    private FirebaseAnalytics firebaseAnalytics;

    private String radioName;
    private String radioURL;
    private int radioImage;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeData();
        loadButtonData();

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.button1:
                sendButtonData();
                break;
        }

    }

    @Override
    public boolean onLongClick(View v) {

        Intent intent = new Intent(this, RadioListActivity.class);

        switch (v.getId()) {
            case R.id.button1:
               intent.putExtra("button", 1);
                startActivity(intent);
                break;
        }
        return false;
    }

    private void initializeData(){

        firebaseAnalytics = FirebaseAnalytics.getInstance(this);

        btn = (ImageButton) findViewById(R.id.button1);

        btn.setOnClickListener(this);
        btn.setOnLongClickListener(this);




    }


    private void loadButtonData() {

        Bundle extra = getIntent().getExtras();

        if (extra != null) {

            this.radioImage = radioImage;
            this.radioURL = radioURL;
            this.radioName = radioName;

            radioName = extra.getString("radioName");
            radioURL = extra.getString("radioURL");
            radioImage = extra.getInt("radioImage");

            int radioImage = extra.getInt("radioImage");

            btn.setImageResource(radioImage);
            btn.getAnimation();


        }

    }


    private void sendButtonData(){

        Intent intent = new Intent(this, PlayActivity.class);

        intent.putExtra("radioImage", radioImage);
        intent.putExtra("radioName", radioName);
        intent.putExtra("radioURL", radioURL);


        startActivity(intent);
    }





}
