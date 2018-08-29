package com.appzelof.skurring.fragments;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.appzelof.skurring.Interfaces.StreamInfoUpdate;
import com.appzelof.skurring.Interfaces.UpdatePlayerUI;
import com.appzelof.skurring.Interfaces.UpdateWeatherUI;
import com.appzelof.skurring.MyMediaPlayer;
import com.appzelof.skurring.R;
import com.appzelof.skurring.activities.MainActivity;
import com.appzelof.skurring.locationHandler.LocationHandler;
import com.appzelof.skurring.model.RadioObject;
import com.appzelof.skurring.networkHandler.XmlDownloader;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlayerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlayerFragment extends Fragment implements StreamInfoUpdate, UpdatePlayerUI, UpdateWeatherUI {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RadioObject choosenRadioStation;
    private TextView radioChannelName, cityName, degrees, songView, speedTextView;
    private ImageView channelImage, weatherImage, speedometerImage;
    private ConstraintLayout constraintLayout;
    private Switch speedSwitcher;
    private Boolean running;
    private ArrayList<String> tempArrayList;

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
        LocationHandler locationHandler = new LocationHandler(getContext(), this);
        locationHandler.getLastKnownLocation(getActivity());
        switchHandler();

        return v;
    }

    private void setupUI(View v) {
        this.channelImage = v.findViewById(R.id.radioLogoImageView);
        this.weatherImage = v.findViewById(R.id.weatherImageView);
        this.speedometerImage = v.findViewById(R.id.speedometerImage);
        this.speedTextView = v.findViewById(R.id.speedTextView);

        this.constraintLayout = v.findViewById(R.id.playerBackground);

        this.radioChannelName = v.findViewById(R.id.radioTitleTextView);
        this.cityName = v.findViewById(R.id.radioCityLabel);
        this.degrees = v.findViewById(R.id.celciusLabel);

        this.speedSwitcher = v.findViewById(R.id.speedSwitch);

        this.songView = v.findViewById(R.id.song);
        this.songView.setSelected(true);

        running = false;
    }

    private void initUI() {
        this.radioChannelName.setText(this.choosenRadioStation.getName());
        this.channelImage.setImageDrawable(getResources().getDrawable(this.choosenRadioStation.getRadioImage()));
    }


    private void initializeBtns(){
        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getContext()).onBackPressed();
            }
        });
    }

    private void switchHandler(){
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
    public void getInfo(String info) {
        this.songView.setText(info);
    }


    @Override
    public void updateLocationInfo(String country, String adminArea, String locality, String subLocality) {
        cityName.setText(locality);
        if (!running && MainActivity.startedOnce) {
            String weatherUrl = "http://www.yr.no/place/" + country + "/" + adminArea + "/" + locality + "/" + subLocality + "/forecast.xml";
            System.out.println(weatherUrl);
            XmlDownloader xmlDownloader = new XmlDownloader(this);
            xmlDownloader.execute(weatherUrl);
            running = true;
            MainActivity.startedOnce = false;
        }

    }

    @Override
    public void updateSpeedInfo(String speed){
        speedTextView.setText(speed);

    }

    @Override
    public void getTempArray(ArrayList<String> temperatures) {
        for (String t : temperatures){
            System.out.println(t);
        }
    }

    @Override
    public void getIconArray(ArrayList<String> iconArray) {

    }
}
