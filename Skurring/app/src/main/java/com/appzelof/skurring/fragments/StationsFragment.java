package com.appzelof.skurring.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appzelof.skurring.R;
import com.appzelof.skurring.activities.MainActivity;
import com.appzelof.skurring.adapter.RadioStationAdapter;
import com.appzelof.skurring.model.RadioObject;
import com.appzelof.skurring.services.RadioData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class StationsFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private RadioStationAdapter radioStationAdapter;
    private ArrayList<RadioObject> radioStations;
    private RecyclerView recyclerView;

    public StationsFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static StationsFragment newInstance(String param1, String param2) {
        StationsFragment fragment = new StationsFragment();
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
        View v = inflater.inflate(R.layout.fragment_stations, container, false);
        initializeRecyclerView(v);
        this.getRadioStationFromFirebase();
        return v;
    }

    public void initializeRadioStationAdapter(boolean fromFirebase) {
        if (fromFirebase) {
            this.radioStationAdapter.updateData(radioStations);
        } else {
            this.radioStationAdapter = new RadioStationAdapter(RadioData.getInstance().getRadioInfoList());
        }
    }

    public RadioStationAdapter getRadioStationAdapter() {
        return this.radioStationAdapter;
    }

    private void getRadioStationFromFirebase() {
        MainActivity.databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                radioStations = new ArrayList<>();
                for (DataSnapshot myDataSnapshot : dataSnapshot.getChildren()) {
                    System.out.println(myDataSnapshot.toString());
                    HashMap radioStation = (HashMap) myDataSnapshot.getValue();
                    RadioObject radioObject = new RadioObject();
                    radioObject.initFromFirebase(radioStation);
                    radioStations.add(new RadioObject().initFromFirebase(radioStation));
                }

                initializeRadioStationAdapter(true);
                radioStationAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //SQL database might be good to atleast get the most updated
                System.out.println("Error: " + databaseError.getMessage());
            }
        });
    }

    private void initializeRecyclerView(View v){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.scrollToPosition(LinearLayoutManager.HORIZONTAL);

        RecyclerView recyclerView = v.findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(radioStationAdapter);

    }



}
