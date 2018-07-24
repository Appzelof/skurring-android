package com.appzelof.skurring_bilradio.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appzelof.skurring_bilradio.R;
import com.appzelof.skurring_bilradio.adapter.RadioStationAdapter;
import com.appzelof.skurring_bilradio.services.RadioData;

public class StationsFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

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
        return v;
    }

    private void initializeComponents(){

    }

    private void initializeRecyclerView(View v){
        RadioStationAdapter radioStationAdapter = new RadioStationAdapter(RadioData.getInstance().getRadioInfoList());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.scrollToPosition(LinearLayoutManager.HORIZONTAL);

        RecyclerView recyclerView = v.findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(radioStationAdapter);

    }

}
