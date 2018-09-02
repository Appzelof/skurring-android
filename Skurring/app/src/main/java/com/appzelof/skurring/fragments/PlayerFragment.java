package com.appzelof.skurring.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import com.appzelof.skurring.Interfaces.ImageDownloaded;
import com.appzelof.skurring.Interfaces.StreamInfoUpdate;
import com.appzelof.skurring.Interfaces.UpdateLocationInfo;
import com.appzelof.skurring.Interfaces.UpdateWeatherInfo;
import com.appzelof.skurring.MyMediaPlayer;
import com.appzelof.skurring.R;
import com.appzelof.skurring.activities.MainActivity;
import com.appzelof.skurring.locationHandler.LocationHandler;
import com.appzelof.skurring.model.PotentialMetadataJSONObject;
import com.appzelof.skurring.model.RadioObject;
import com.appzelof.skurring.networkHandler.ImageDownloader;
import com.appzelof.skurring.networkHandler.XmlDownloader;
import com.appzelof.skurring.sharePrefsManager.SharePrefsManager;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlayerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlayerFragment extends Fragment implements StreamInfoUpdate, UpdateLocationInfo, UpdateWeatherInfo, ImageDownloaded {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RadioObject choosenRadioStation;
    private TextView radioChannelName, cityName, degrees, songView, speedTextView;
    private ImageView channelImage, weatherImage, speedometerImage, albumImage;
    private ConstraintLayout constraintLayout;
    private ProgressBar weatherProgressBar;
    private ProgressBar tempProgressBar;
    private Switch speedSwitcher;
    private SharePrefsManager sharePrefsManager;
    private XmlDownloader xmlDownloader;
    private LocationHandler locationHandler;
    private Boolean checked;
    private static String WEATHER_URL;
    private ImageDownloader imageDownloader;


    private GoogleApiClient googleApiClient;


    public PlayerFragment() {
        // Required empty public constructor

    }


    // TODO: Rename and change types and number of parameters
    public static PlayerFragment newInstance(String param1, String param2) {
        PlayerFragment fragment = new PlayerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        System.out.println(this.choosenRadioStation.getName());
        View v = inflater.inflate(R.layout.fragment_player, container, false);
        this.setupUI(v);
        this.initUI();
        initializeBtns();
        MyMediaPlayer.INSTANCE = new MyMediaPlayer();
        MyMediaPlayer.INSTANCE.initAndPrepareAndPlay(this.choosenRadioStation.getUrl());
        MyMediaPlayer.INSTANCE.streamInfoUpdate = this;
        locationHandler = new LocationHandler(getContext(), this);
        locationHandler.getLastKnownLocation(getActivity());
        sharePrefsManager = new SharePrefsManager(getContext());
        switchHandler();
        checked = false;

        return v;
    }

    private void setupUI(View v) {
        this.channelImage = v.findViewById(R.id.radioLogoImageView);
        this.weatherImage = v.findViewById(R.id.weatherImageView);
        this.speedometerImage = v.findViewById(R.id.speedometerImage);
        this.speedTextView = v.findViewById(R.id.speedTextView);
        this.tempProgressBar = v.findViewById(R.id.degreesProgressBar2);
        this.weatherProgressBar = v.findViewById(R.id.sunProgressBar);
        this.constraintLayout = v.findViewById(R.id.playerBackground);
        this.albumImage = v.findViewById(R.id.albumImage);

        this.radioChannelName = v.findViewById(R.id.radioTitleTextView);
        this.cityName = v.findViewById(R.id.radioCityLabel);
        this.degrees = v.findViewById(R.id.celciusLabel);

        this.speedSwitcher = v.findViewById(R.id.speedSwitch);

        this.songView = v.findViewById(R.id.song);
        this.songView.setSelected(true);


    }

    private void initUI() {
        this.radioChannelName.setText(this.choosenRadioStation.getName());
        this.channelImage.setImageDrawable(getResources().getDrawable(this.choosenRadioStation.getRadioImage()));
    }


    private void initializeBtns() {
        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getContext()).onBackPressed();
            }
        });
    }

    private void switchHandler() {
        speedSwitcher.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    speedTextView.setVisibility(View.VISIBLE);
                    channelImage.setVisibility(View.INVISIBLE);
                } else {
                    speedTextView.setVisibility(View.INVISIBLE);
                    channelImage.setVisibility(View.VISIBLE);
                }

            }
        });

    }


    @Override
    public void getInfo(String album, String artist, PotentialMetadataJSONObject potentialMetadataJSONObject) {
        String viewString = "";
        if (potentialMetadataJSONObject != null) {
            if (!potentialMetadataJSONObject.getTitle().equals("")) {
                viewString += potentialMetadataJSONObject.getTitle();
            }
            if (!potentialMetadataJSONObject.getArtist().equals("")) {
                viewString += " - ";
                viewString += potentialMetadataJSONObject.getArtist();
            }
            if (!potentialMetadataJSONObject.getImageUrl().equals("")) {
                initializeImageDownloader();
                this.imageDownloader.downloadImageFromPureUrl(potentialMetadataJSONObject.getImageUrl());
            }
            this.songView.setText(viewString);
        } else {
            if (!album.equals("") && !artist.equals("")) {
                viewString += album + " - " + artist;
                initializeImageDownloader();
                this.imageDownloader.downloadImageFromParts(artist, album);
                this.songView.setText(viewString);
            } else {
                initializeImageDownloader();
                this.imageDownloader.downloadImageFromPart(album);
                this.songView.setText(album);
            }
        }
    }

    private void initializeImageDownloader() {
        this.imageDownloader = new ImageDownloader();
        imageDownloader.imageDownloaded = this;
    }

    @Override
    public void imageDownloaded(Bitmap imageDownloaded) {
        this.albumImage.setImageBitmap(imageDownloaded);
    }

    @Override
    public void imageJSONURL(String url) {
        //Not needed. have to implement it.
    }

    @Override
    public void errorGettingImageFromJSON() {
        //When there is no image available from JSON
        this.albumImage.setImageResource(android.R.color.transparent);
    }


    @Override
    public void updateLocationInfo(String country, String adminArea, String postalNumber) {
        WEATHER_URL = "https://www.yr.no/place/" + country + "/Postnummer/" + postalNumber + "/forecast.xml";
        XmlDownloader xmlDownloader = new XmlDownloader(getContext(), this);
        xmlDownloader.execute(WEATHER_URL);
        sharePrefsManager.saveString("city", adminArea);
        cityName.setText(adminArea);
        System.out.println("Runned one time");
        checked = true;
        updateUI.update(checked);

    }

    @Override
    public void parsed(Boolean parsed) {
        if (parsed){
            loadIconAndTemp();
        }
    }


    private interface UpdateUI{
        void update(Boolean checked);
    }

    UpdateUI updateUI = new UpdateUI() {
        @Override
        public void update(Boolean checked) {
            if (checked){
                loadIconAndTemp();
            }
        }
    };

    @Override
    public void updateAdminArea(String adminArea) {
        cityName.setText(adminArea);
        if (!adminArea.equalsIgnoreCase(sharePrefsManager.getString("city")) && checked){
            XmlDownloader xmlDownloader = new XmlDownloader(getContext(),this);
            xmlDownloader.execute(WEATHER_URL);
            sharePrefsManager.saveString("city", adminArea);
            updateUI.update(checked);
            checked = false;
        } else {
            System.out.println("TEST");
        }
    }

    @Override
    public void updateSpeedInfo(String speed){
        speedTextView.setText(speed);

    }

    private void loadIconAndTemp(){
        String savedImage = sharePrefsManager.getString("symbol");
        int id = getContext().getResources().getIdentifier("drawable/" + "y" + savedImage, null, getContext().getPackageName());
        String temp = sharePrefsManager.getString("temp");

        tempProgressBar.setVisibility(View.INVISIBLE);
        weatherProgressBar.setVisibility(View.INVISIBLE);
        degrees.setText(temp + "º");
        weatherImage.setImageResource(id);

    }

}
