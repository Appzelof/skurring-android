package com.appzelof.skurring.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.appzelof.skurring.Interfaces.StreamInfoUpdate;
import com.appzelof.skurring.MyMediaPlayer;
import com.appzelof.skurring.R;
import com.appzelof.skurring.model.RadioObject;

import java.net.URI;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlayerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlayerFragment extends Fragment implements StreamInfoUpdate {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RadioObject choosenRadioStation;
    private TextView radioChannelName, cityName, degrees, songView;
    private ImageView channelImage, weatherImage, speedometerImage;
    private Switch speedSwithcer;


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
        MyMediaPlayer.INSTANCE = new MyMediaPlayer();
        MyMediaPlayer.INSTANCE.initAndPrepareAndPlay(this.choosenRadioStation.getUrl());
        MyMediaPlayer.INSTANCE.streamInfoUpdate = this;
        return v;
    }

    private void setupUI(View v) {
        this.channelImage = v.findViewById(R.id.radioLogoImageView);
        this.weatherImage = v.findViewById(R.id.weatherImageView);
        this.speedometerImage = v.findViewById(R.id.speedometerImage);

        this.radioChannelName = v.findViewById(R.id.radioTitleTextView);
        this.cityName = v.findViewById(R.id.radioCityLabel);
        this.degrees = v.findViewById(R.id.celciusLabel);

        this.speedSwithcer = v.findViewById(R.id.speedSwitch);

        this.songView = v.findViewById(R.id.song);
    }

    private void initUI() {
        this.radioChannelName.setText(this.choosenRadioStation.getName());
        this.channelImage.setImageDrawable(getResources().getDrawable(this.choosenRadioStation.getRadioImage()));
    }


    @Override
    public void getInfo(String info) {
        this.songView.setText(info);
    }
}
