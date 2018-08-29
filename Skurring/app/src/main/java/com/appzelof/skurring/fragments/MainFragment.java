package com.appzelof.skurring.fragments;


import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.appzelof.skurring.MyMediaPlayer;
import com.appzelof.skurring.R;
import com.appzelof.skurring.SQLite.DatabaseManager;
import com.appzelof.skurring.activities.MainActivity;
import com.appzelof.skurring.model.RadioObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;


public class MainFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ImageButton imageButton;
    private ImageButton imageButton2;
    private ImageButton imageButton3;
    private ImageButton imageButton4;
    private ImageButton imageButton5;
    private ImageButton imageButton6;
    private ImageButton imageButton7;

    private SparseArray<ImageButton> imageButtonSparseArray;
    public StationsFragment stationsFragment;
    public static int changingRadioChannel = 0;
    private ArrayList<RadioObject> radioList;


    public MainFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
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
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        initializeComponents(v);
        imageButtonHandler();


        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        this.updateUI();
        if (MyMediaPlayer.INSTANCE != null && MyMediaPlayer.INSTANCE.getMediaPlayer() != null) {
            MyMediaPlayer.INSTANCE.stopMediaPlayerAndMetadataRecording();
        }
    }

    private void initializeComponents(View v){

        imageButton = v.findViewById(R.id.imageButton);
        imageButton2 = v.findViewById(R.id.imageButton2);
        imageButton3 = v.findViewById(R.id.imageButton3);
        imageButton4 = v.findViewById(R.id.imageButton4);
        imageButton5 = v.findViewById(R.id.imageButton5);
        imageButton6 = v.findViewById(R.id.imageButton6);
        imageButton7 = v.findViewById(R.id.imageButton7);

        imageButtonSparseArray = new SparseArray<>();

        imageButtonSparseArray.append(1, imageButton);
        imageButtonSparseArray.append(2, imageButton2);
        imageButtonSparseArray.append(3, imageButton3);
        imageButtonSparseArray.append(4, imageButton4);
        imageButtonSparseArray.append(5, imageButton5);
        imageButtonSparseArray.append(6, imageButton6);
        imageButtonSparseArray.append(7, imageButton7);

    }

    private void imageButtonHandler(){
        for (int i = 1; i < 8; i++){
            System.out.println(i);
            final int chosenChaningChannel = i;
            imageButtonSparseArray.get(i).setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                        ((MainActivity) getContext()).replaceFragment(stationsFragment, R.id.main_container);
                        MainFragment.changingRadioChannel = chosenChaningChannel;
                    System.out.println(chosenChaningChannel);
                        return false;
                    }
            });

            final int choosenRadioStation = i;
            imageButtonSparseArray.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PlayerFragment playerFragment = new PlayerFragment();
                    RadioObject radioObject = searchForClickedRadiostation(choosenRadioStation);
                    if (radioObject != null) {
                        playerFragment.choosenRadioStation = radioObject;
                        ((MainActivity) getContext()).replaceFragment(playerFragment, R.id.main_container);
                    }
                }
            });
            imageButtonSparseArray.get(i).setScaleType(ImageView.ScaleType.FIT_CENTER);
        }
        this.updateUI();
    }

    private void updateUI() {
        this.sortRadioStationList();
        for (RadioObject radioObject: this.radioList) {
            Drawable theImage = getResources().getDrawable(radioObject.getRadioImage());
            this.imageButtonSparseArray.get(radioObject.getChoosenSpot()).setImageDrawable(theImage);
        }
    }

    private RadioObject searchForClickedRadiostation(int tapped) {
        for (RadioObject radioObject: this.radioList) {
            if (tapped == radioObject.getChoosenSpot()) {
                return radioObject;
            }
        }
        return null;
    }

    private void sortRadioStationList() {
        this.radioList = DatabaseManager.INSTANCE.getAllSavedStations();
        Collections.sort(radioList, new Comparator<RadioObject>() {
            @Override
            public int compare(RadioObject radioObject, RadioObject t1) {
                if (radioObject.getChoosenSpot() < t1.getChoosenSpot()) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });
    }
}
