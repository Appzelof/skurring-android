package com.appzelof.skurring.activityViews;

import android.content.Intent;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;

import com.appzelof.skurring.R;
import com.appzelof.skurring.TinyDB.TinyDB;
import com.appzelof.skurring.model.RadioObject;
import com.google.android.gms.ads.AdRequest;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {

    private ImageButton btn, btn2, btn3, btn4, btn5, btn6, btn7;
    private FirebaseAnalytics firebaseAnalytics;
    public static ArrayList<RadioObject> list;
    public static TinyDB tinyDB;

    private String radioName;
    private String radioURL;
    private int radioImage;
    private Vibrator vibrator;

    @Override
    public void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        initializeData();
        loadButtonData();
        updateButtonUI();

    }
    
    @Override
    public void onClick(View v) {

        Intent intent = new Intent(this, PlayActivity.class);

        Bundle extra = getIntent().getExtras();

            switch (v.getId()) {

                case R.id.button1:
                    if (tinyDB.getInt("image") != 0) {
                        radioImage = tinyDB.getInt("image");
                        intent.putExtra("radioImage", radioImage);
                        intent.putExtra("radioName", tinyDB.getString("name"));
                        intent.putExtra("radioURL", tinyDB.getString("url"));
                        startActivity(intent);
                    } else {
                        showToast();
                    }

                    break;

                case R.id.button2:
                    if (tinyDB.getInt("image2") != 0) {
                        radioImage = tinyDB.getInt("image2");
                        intent.putExtra("radioImage", radioImage);
                        intent.putExtra("radioName", tinyDB.getString("name2"));
                        intent.putExtra("radioURL", tinyDB.getString("url2"));
                        startActivity(intent);
                    } else {
                        showToast();
                    }
                    break;

                case R.id.button3:
                    if (tinyDB.getInt("image3") != 0) {

                        radioImage = tinyDB.getInt("image3");
                        intent.putExtra("radioImage", radioImage);
                        intent.putExtra("radioName", tinyDB.getString("name3"));
                        intent.putExtra("radioURL", tinyDB.getString("url3"));
                        startActivity(intent);
                    } else {
                        showToast();
                    }
                    break;

                case R.id.button4:
                    if (tinyDB.getInt("image4") != 0) {
                        radioImage = tinyDB.getInt("image4");
                        intent.putExtra("radioImage", radioImage);
                        intent.putExtra("radioName", tinyDB.getString("name4"));
                        intent.putExtra("radioURL", tinyDB.getString("url4"));
                        startActivity(intent);
                    } else {
                        showToast();
                    }
                    break;

                case R.id.button5:
                    if (tinyDB.getInt("image5") != 0) {
                        radioImage = tinyDB.getInt("image5");
                        intent.putExtra("radioImage", radioImage);
                        intent.putExtra("radioName", tinyDB.getString("name5"));
                        intent.putExtra("radioURL", tinyDB.getString("url5"));
                        startActivity(intent);
                    } else {
                        showToast();
                    }
                    break;

                case R.id.button6:
                    if (tinyDB.getInt("image6") != 0) {
                        radioImage = tinyDB.getInt("image6");
                        intent.putExtra("radioImage", radioImage);
                        intent.putExtra("radioName", tinyDB.getString("name6"));
                        intent.putExtra("radioURL", tinyDB.getString("url6"));
                        startActivity(intent);
                    } else {
                        showToast();
                    }
                    break;

                case R.id.button7:
                    Toast.makeText(this, "Comming soon", Toast.LENGTH_LONG).show();
            }
        }



    @Override
    public boolean onLongClick(View v) {
        Intent intent = new Intent(this, RadioListActivity.class);
        finish();
        switch (v.getId()) {
            case R.id.button1:
                intent.putExtra("button", 1);
                vibes();
                startActivity(intent);
                break;
            case R.id.button2:
                intent.putExtra("button2", 2);
                vibes();
                startActivity(intent);
                break;
            case R.id.button3:
                intent.putExtra("button3", 3);
                vibes();
                startActivity(intent);
                break;
            case R.id.button4:
                intent.putExtra("button4", 4);
                vibes();
                startActivity(intent);
                break;
            case R.id.button5:
                intent.putExtra("button5", 5);
                vibes();
                startActivity(intent);
                break;
            case R.id.button6:
                intent.putExtra("button6", 6);
                vibes();
                startActivity(intent);
                break;
        }
        return false;
    }

    private void initializeData() {
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);

        list = new ArrayList<>();
        tinyDB = new TinyDB(this);

        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        btn = (ImageButton) findViewById(R.id.button1);
        btn2 = (ImageButton) findViewById(R.id.button2);
        btn3 = (ImageButton) findViewById(R.id.button3);
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
            if (extra.getInt("btn1") == 1) {
                tinyDB.putString("name", radioName);
                tinyDB.putString("url", radioURL);
                tinyDB.putInt("image", radioImage);
            }
            if (extra.getInt("btn2") == 2) {
                tinyDB.putString("name2", radioName);
                tinyDB.putString("url2", radioURL);
                tinyDB.putInt("image2", radioImage);
            }
            if (extra.getInt("btn3") == 3) {
                tinyDB.putString("name3", radioName);
                tinyDB.putString("url3", radioURL);
                tinyDB.putInt("image3", radioImage);
            }
            if (extra.getInt("btn4") == 4) {
                tinyDB.putString("name4", radioName);
                tinyDB.putString("url4", radioURL);
                tinyDB.putInt("image4", radioImage);
            }
            if (extra.getInt("btn5") == 5) {
                tinyDB.putString("name5", radioName);
                tinyDB.putString("url5", radioURL);
                tinyDB.putInt("image5", radioImage);
            }
            if (extra.getInt("btn6") == 6) {
                tinyDB.putString("name6", radioName);
                tinyDB.putString("url6", radioURL);
                tinyDB.putInt("image6", radioImage);
            }

        }

    }

    private void updateButtonUI() {

        Bundle extra = getIntent().getExtras();
        if (tinyDB.getInt("image") != 0) {
            btn.setImageResource(tinyDB.getInt("image"));
        } else {
            btn.setImageResource(R.drawable.hold);
        }

        if (tinyDB.getInt("image2") != 0) {
            btn2.setImageResource(tinyDB.getInt("image2"));
        } else {
            btn2.setImageResource(R.drawable.hold);
        }
        if (tinyDB.getInt("image3") != 0){
            btn3.setImageResource(tinyDB.getInt("image3"));
        } else {
            btn3.setImageResource(R.drawable.hold);
        }
        if (tinyDB.getInt("image4") != 0){
            btn4.setImageResource(tinyDB.getInt("image4"));
        } else {
            btn4.setImageResource(R.drawable.hold);
        }
        if (tinyDB.getInt("image5") != 0){
            btn5.setImageResource(tinyDB.getInt("image5"));
        } else {
            btn5.setImageResource(R.drawable.hold);
        }
        if (tinyDB.getInt("image6") != 0){
            btn6.setImageResource(tinyDB.getInt("image6"));
        } else {
            btn6.setImageResource(R.drawable.hold);
        }
    }

    private void showToast() {
        Toast.makeText(this, "Hold knappen inne", Toast.LENGTH_SHORT).show();
    }

    public void vibes() {
        vibrator.vibrate(5);
    }


}