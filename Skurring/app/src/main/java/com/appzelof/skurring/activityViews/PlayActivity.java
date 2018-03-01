package com.appzelof.skurring.activityViews;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.appzelof.skurring.Interface.LiveData;
import com.appzelof.skurring.Interface.WeatherUpdate;
import com.appzelof.skurring.R;
import com.appzelof.skurring.TinyDB.TinyDB;
import com.appzelof.skurring.mediaPlayer.SoundPlayer;
import com.appzelof.skurring.model.WeatherObject;
import com.appzelof.skurring.xml.ParseData;
import com.appzelof.skurring.xml.XmlParser;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;


import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class PlayActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, BillingProcessor.IBillingHandler, LiveData, WeatherUpdate {

    private View playView;
    private SoundPlayer soundPlayer;
    private ImageView imageView, speedImageView, weatherImage;
    private TextView textView, speedTxt, kmText, cityText, tempText;
    private String radioURL, radioName;
    private int radioImage;
    private Uri urlStream;
    private LocationManager locationManager;
    private final int PERMISION_ALL = 1;
    private final String[] PERMISSIONS = {Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
    private SwitchCompat aSwitch;
    private Context context;
    private AdView myBannerAdView;
    private AdRequest adRequest;
    private BillingProcessor bp;
    private Button premBtn;
    private TinyDB tinyDB;
    private Geocoder geocoder;
    private XmlParser xmlParser;
    public static String endPoint;
    private ParseData parseData;
    private ProgressBar progressBar;
    boolean stopper;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        initializeData();
        loadAds();
        loadAlreadyPurchasedContent();
        loadCheckedState();
        loadSpeedAndLocation();


    }

    @Override
    public void getLiveDataString(String data) {
        if (data != null) {
            System.out.println("The live data: " + data);
        } else {
            System.out.println("could not fetch live data");
        }
    }

    private void initializeData() {

        stopper = false;

        soundPlayer = new SoundPlayer();
        soundPlayer.liveData = this;
        parseData = new ParseData();
        imageView = (ImageView) findViewById(R.id.my_play_image);
        weatherImage = (ImageView) findViewById(R.id.my_weather_img);
        speedImageView = (ImageView) findViewById(R.id.speed_image);
        progressBar = (ProgressBar) findViewById(R.id.my_progressBar);

        textView = (TextView) findViewById(R.id.my_radio_play_name);
        speedTxt = (TextView) findViewById(R.id.speedTextView);
        kmText = (TextView) findViewById(R.id.my_km_text);
        cityText = (TextView) findViewById(R.id.my_city_text);
        tempText = (TextView) findViewById(R.id.my_temp_text);

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

        bp = new BillingProcessor(this, null, this);

        Typeface customFont = Typeface.createFromAsset(getAssets(), "fonts/digital.ttf");
        speedTxt.setTypeface(customFont);
        kmText.setTypeface(customFont);

        tinyDB = new TinyDB(this);
        tinyDB.putString("stream", getRadioURL());


    }

    @Override
    public void getUpdatedWeatherData(WeatherObject weatherObject) {
        System.out.println("FÅR DATA: " + weatherObject.getTemp());
        this.updateUI(weatherObject);
    }

    private void loadAds() {
        MobileAds.initialize(this, "\n" +
                "ca-app-pub-5770694165805669/3714218614");
        adRequest = new AdRequest.Builder().build();
        myBannerAdView.loadAd(adRequest);
    }

    private void loadAlreadyPurchasedContent() {
        if (tinyDB.getBoolean("saveP") == true) {
            removeAds();
        }
    }

    private void loadCheckedState() {
        if (tinyDB.getBoolean("checked") == true) {
            aSwitch.setChecked(true);

        } else if (tinyDB.getBoolean("checked") == false) {
            aSwitch.setChecked(false);
        }
    }

    public boolean removeAds() {
        speedImageView.setVisibility(View.VISIBLE);
        aSwitch.setVisibility(View.VISIBLE);
        myBannerAdView.setVisibility(View.INVISIBLE);
        premBtn.setVisibility(View.INVISIBLE);
        weatherImage.setVisibility(View.VISIBLE);
        tempText.setVisibility(View.VISIBLE);
        cityText.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);

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
            speedImageView.setImageResource(R.drawable.green_speed);
            speedTxt.setVisibility(View.VISIBLE);
            kmText.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.INVISIBLE);
            tinyDB.putBoolean("checked", true);
            Toast.makeText(this, "Speedometer aktivert", Toast.LENGTH_SHORT).show();
        } else {
            speedImageView.setImageResource(R.drawable.speed);
            speedTxt.setVisibility(View.INVISIBLE);
            imageView.setVisibility(View.VISIBLE);
            kmText.setVisibility(View.INVISIBLE);
            tinyDB.putBoolean("checked", false);
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
        if (!bp.handleActivityResult(requestCode, resultCode, data)) ;
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        if (bp != null) {
            bp.release();
            super.onDestroy();
        }
    }

    public void loadSpeedAndLocation() {
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                double kMH = location.getSpeed() * 3.6;
                if (aSwitch.isChecked()) {
                    speedTxt.setText(String.valueOf(Math.round(kMH)));
                    if (kMH < 1) {
                        speedTxt.setText("0");
                    }
                }

                try {
                    geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                    List<Address> addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    String countryCode = addressList.get(0).getCountryCode();
                    String country = addressList.get(0).getCountryName();
                    String adminArea = addressList.get(0).getAdminArea();
                    String locality = addressList.get(0).getLocality();
                    String subLocality = addressList.get(0).getSubLocality();
                    final String postalCode = addressList.get(0).getPostalCode();


                    cityText.setText(locality);
                    endPoint = "https://www.yr.no/place/" + country + "/postnummer/" + postalCode + "/forecast.xml";


                    if (endPoint !=  null && !stopper) {
                            startParsing(endPoint);
                        }

                        stopper = true;




                } catch (IOException e) {
                    e.printStackTrace();
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

    private void startParsing(String urlString) {
        if (urlString != null){
            XmlParser xmlParser = new XmlParser(this);
        xmlParser.execute(urlString);
    }

    }

    public void updateUI(WeatherObject weatherObject) {
        String xmlTemp = weatherObject.getTemp();
        System.out.println("TEMP: " + xmlTemp);

        if (endPoint != null) {
            if (xmlTemp != null) {
                int temperature = Integer.parseInt(xmlTemp);
                if (temperature <= 0) {
                    tempText.setTextColor(getResources().getColor(R.color.cold_blue));
                } else {
                    tempText.setTextColor(getResources().getColor(R.color.warm_orange));
                }
                tempText.setText(temperature + "");
                tinyDB.putInt("temperature", temperature);
                progressBar.setVisibility(View.INVISIBLE);
            }
            String img = weatherObject.getImage();
            System.out.println("IMGG: " + img);
            if (img != null) {
                System.out.println();
                String myImage = "y" + img;
                int resId = getResources().getIdentifier(myImage, "drawable", getPackageName());
                System.out.println(resId);
                tinyDB.putInt("res", resId);
                weatherImage.setImageResource(resId);
            }
        }
    }

}











