package com.appzelof.skurring.activityViews;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.appzelof.skurring.R;
import com.appzelof.skurring.TinyDB.TinyDB;
import com.appzelof.skurring.mediaPlayer.SoundPlayer;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.squareup.picasso.Picasso;


import java.util.Locale;


public class PlayActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, BillingProcessor.IBillingHandler{

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
    private SwitchCompat aSwitch;
    private Context context;
    private AdView myBannerAdView;
    private AdRequest adRequest;
    private BillingProcessor bp;
    private Button premBtn;
    private TinyDB tinyDB;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        initializeData();
        loadAds();
        loadAllreadyBuyedPurchase();
    }


    private void initializeData() {


        soundPlayer = new SoundPlayer();

        imageView = (ImageView) findViewById(R.id.my_play_image);
        speedImageView = (ImageView) findViewById(R.id.speed_image);

        textView = (TextView) findViewById(R.id.my_radio_play_name);
        speedTxt = (TextView) findViewById(R.id.speedTextView);

        playView = (View) findViewById(R.id.playView);
        locationManager = (LocationManager) getSystemService(context.LOCATION_SERVICE);
        myBannerAdView = (AdView) findViewById(R.id.adView);

        aSwitch = (SwitchCompat) findViewById(R.id.switch1);
        premBtn = (Button) findViewById(R.id.prem_btn);
        premBtn.setOnClickListener(this);
        playView.setOnClickListener(this);
        aSwitch.setOnCheckedChangeListener(this);

        textView.setText(getRadioName());
        imageView.setImageResource(getRadioImage());
        urlStream = Uri.parse(getRadioURL());
        soundPlayer.play(getApplicationContext(), urlStream);


        bp = new BillingProcessor(this, null,this);



        Typeface customFont  = Typeface.createFromAsset(getAssets(), "fonts/digital.ttf");
        speedTxt.setTypeface(customFont);


        tinyDB = new TinyDB(this);



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

    private void loadAds() {
        MobileAds.initialize(this, "\n" +
                "ca-app-pub-5770694165805669/3714218614");

        adRequest = new AdRequest.Builder().build();
        myBannerAdView.loadAd(adRequest);
    }

    private void loadAllreadyBuyedPurchase() {

        if (tinyDB.getBoolean("saveP") == true) {
            removeAds();
        }
    }


    public boolean removeAds() {
        speedImageView.setVisibility(View.VISIBLE);
        aSwitch.setVisibility(View.VISIBLE);
        myBannerAdView.setVisibility(View.INVISIBLE);
        premBtn.setVisibility(View.INVISIBLE);


        return true;
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.playView:
                soundPlayer.stop();
                goBack();
                break;
            case R.id.prem_btn:
                bp.purchase(this, "android.test.purchased");
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
            Toast.makeText(this, "Speedometer aktivert", Toast.LENGTH_SHORT).show();
        } else {
            Picasso.with(this).load(R.drawable.speed).into(speedImageView);
            speedTxt.setVisibility(View.INVISIBLE);
            imageView.setVisibility(View.VISIBLE);
            Toast.makeText(this, "Speedometer deaktivert", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        soundPlayer.stop();
        goBack();

    }


    @Override
    public void onProductPurchased(@NonNull String productId, @Nullable TransactionDetails details) {
        Toast.makeText(this, "purchased", Toast.LENGTH_SHORT).show();
        removeAds();

        if (removeAds() == true) {
            tinyDB.putBoolean("saveP", removeAds());
        }
    }

    @Override
    public void onPurchaseHistoryRestored() {


    }

    @Override
    public void onBillingError(int errorCode, @Nullable Throwable error) {

    }

    @Override
    public void onBillingInitialized() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!bp.handleActivityResult(requestCode, resultCode, data));
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        if (bp != null) {
            bp.release();

            super.onDestroy();
        }
    }
}





