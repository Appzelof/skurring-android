package com.appzelof.skurring.activityViews;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import com.appzelof.skurring.R;
import com.google.firebase.analytics.FirebaseAnalytics;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener{

    private ImageButton btn;
    private ImageButton btn2;
    private ImageButton btn3;
    private ImageButton btn4;
    private ImageButton btn5;
    private ImageButton btn6;
    private ImageButton btn7;

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
            case R.id.button2:
                sendButtonData();
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
            case R.id.button2:
                startActivity(intent);
                break;
            case R.id.button3:
                startActivity(intent);
                break;
            case R.id.button4:
                startActivity(intent);
                break;
            case R.id.button5:
                startActivity(intent);
                break;
            case R.id.button6:
                startActivity(intent);
                break;
        }
        return false;
    }

    private void initializeData(){

        firebaseAnalytics = FirebaseAnalytics.getInstance(this);

        btn = (ImageButton) findViewById(R.id.button1);
        btn2 = (ImageButton) findViewById(R.id.button2);
        btn3 =  (ImageButton) findViewById(R.id.button3);
        btn4 = (ImageButton) findViewById(R.id.button4);
        btn5 = (ImageButton) findViewById(R.id.button5);
        btn6 = (ImageButton) findViewById(R.id.button6);
        btn7 = (ImageButton) findViewById(R.id.button7);

        btn.setOnClickListener(this);
        btn.setOnLongClickListener(this);

        btn2.setOnClickListener(this);
        btn2.setOnLongClickListener(this);

        btn3.setOnClickListener(this);
        btn3.setOnLongClickListener(this);

        btn4.setOnClickListener(this);
        btn4.setOnLongClickListener(this);

        btn5.setOnClickListener(this);
        btn5.setOnLongClickListener(this);

        btn6.setOnClickListener(this);
        btn6.setOnLongClickListener(this);

        btn7.setOnClickListener(this);
        btn7.setOnLongClickListener(this);



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
