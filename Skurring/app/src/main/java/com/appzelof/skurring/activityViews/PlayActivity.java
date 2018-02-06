package com.appzelof.skurring.activityViews;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.appzelof.skurring.R;
import com.appzelof.skurring.mediaPlayer.SoundPlayer;
import com.squareup.picasso.Picasso;


public class PlayActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private View playView;
    private SoundPlayer soundPlayer;
    private ImageView imageView, speedImageView;
    private TextView textView;
    private String radioURL;
    private String radioName;
    private int radioImage;
    private Uri urlStream;
    private LocationManager locationManager;
    private final int PERMISION_ALL = 1;
    private final String[] PERMISSIONS = {Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
    private TextView speedTxt;
    private Switch aSwitch;
    private Context context;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        initializeData();


        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                location.getLatitude();
                double kMH = location.getSpeed() * 3.6;

                if (aSwitch.isChecked()){
                speedTxt.setText(String.valueOf(Math.round(kMH)));

                if (kMH < 1) {
                    speedTxt.setText("0");
                }

                }

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISION_ALL);

            return;
        }
        
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locationListener);

    }

    private void initializeData() {



        soundPlayer = new SoundPlayer();
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        imageView = (ImageView) findViewById(R.id.my_play_image);
        speedImageView = (ImageView) findViewById(R.id.speed_image);
        textView = (TextView) findViewById(R.id.my_radio_play_name);
        speedTxt = (TextView) findViewById(R.id.speedTextView);
        playView = (View) findViewById(R.id.playView);
        locationManager = (LocationManager) getSystemService(context.LOCATION_SERVICE);
        aSwitch = (Switch) findViewById(R.id.switch1);


        textView.setText(getRadioName());
        imageView.setImageResource(getRadioImage());
        urlStream = Uri.parse(getRadioURL());
        soundPlayer.play(getApplicationContext(), urlStream);

        playView.setOnClickListener(this);
        aSwitch.setOnCheckedChangeListener(this);



    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.playView:
                soundPlayer.stop();
                goBack();
                break;
        }

    }

    private int getRadioImage() {
        Bundle extra = getIntent().getExtras();

        if (extra != null) {
            radioImage = extra.getInt("radioImage");
        }


        return radioImage;
    }

    private String getRadioURL() {
        Bundle extra = getIntent().getExtras();

        if (extra != null) {
            radioURL = extra.getString("radioURL");
            System.out.println(radioURL);
        }

        return radioURL;
    }

    private String getRadioName() {
        Bundle extra = getIntent().getExtras();

        if (extra != null) {
            radioName = extra.getString("radioName");
        }

        return radioName;
    }


    private void goBack() {
        finish();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        if (aSwitch.isChecked()) {
            Picasso.with(this).load(R.drawable.green_speed).into(speedImageView);
            speedTxt.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.INVISIBLE);
        } else {
            Picasso.with(this).load(R.drawable.speed).into(speedImageView);
            speedTxt.setVisibility(View.INVISIBLE);
            imageView.setVisibility(View.VISIBLE);
        }

    }
}





