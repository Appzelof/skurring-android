package com.appzelof.skurring.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appzelof.skurring.Enums.DataToAdapterFrom;
import com.appzelof.skurring.networkHandler.InternetAccessChecker;
import com.appzelof.skurring.R;
import com.appzelof.skurring.SQLiteFirebase.DatabaseManager;
import com.appzelof.skurring.activities.MainActivity;
import com.appzelof.skurring.adapter.RadioStationAdapter;
import com.appzelof.skurring.model.RadioObject;
import com.appzelof.skurring.services.RadioData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class StationsFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private RadioStationAdapter radioStationAdapter;
    private ArrayList<RadioObject> radioStations;
    private InternetAccessChecker internetAccessChecker;
    public RecyclerView recyclerView;

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
        internetAccessChecker = new InternetAccessChecker();
        initializeRecyclerView(v);
        this.getRadioStationFromFirebase();
        return v;
    }

    public void initializeRadioStationAdapter(DataToAdapterFrom dataToAdapterFrom, ArrayList<RadioObject> arrayList) {
        switch (dataToAdapterFrom) {
            case FIREBASE:
                this.radioStationAdapter.updateData(arrayList);
                break;
            case INAPP:
                this.radioStationAdapter = new RadioStationAdapter(sortList(RadioData.getInstance().getRadioInfoList()));
                break;
            case SQLITE:
                this.radioStationAdapter.updateData(arrayList);
                break;
            case INAPPUPDATE:
                this.radioStationAdapter.updateData(arrayList);
                break;
                default: break;
        }
    }

    private ArrayList<RadioObject> sortList(ArrayList<RadioObject> arrayList) {
        java.util.Collections.sort(arrayList, new Comparator<RadioObject>() {
            @Override
            public int compare(RadioObject o, RadioObject t1) {
                return o.getName().compareTo(t1.getName());
            }
        });
        return arrayList;
    }

    public RadioStationAdapter getRadioStationAdapter() {
        return this.radioStationAdapter;
    }

    private void getRadioStationFromFirebase() {
        if (this.internetAccessChecker.haveInternetConnection(this.getContext())) {
            MainActivity.databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    radioStations = new ArrayList<>();
                    for (DataSnapshot myDataSnapshot : dataSnapshot.getChildren()) {
                        HashMap radioStation = (HashMap) myDataSnapshot.getValue();
                        RadioObject radioObject = new RadioObject();
                        radioObject.initFromFirebase(radioStation);
                        radioStations.add(new RadioObject().initFromFirebase(radioStation));
                    }
                    radioStationAdapter.updateData(radioStations);
                    initializeRadioStationAdapter(DataToAdapterFrom.FIREBASE, handleSQLiteList().get(0));
                    radioStationAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    configureRadioListIfNoInternetOrCanceled();
                    System.out.println("Error: " + databaseError.getMessage());
                }
            });
        } else {
            configureRadioListIfNoInternetOrCanceled();
        }

    }

    private void configureRadioListIfNoInternetOrCanceled() {
        ArrayList<RadioObject> savedList = DatabaseManager.INSTANCE.getAllRadioStations();
        if (!savedList.isEmpty()) {
            System.out.println("Using SQLite!");
            initializeRadioStationAdapter(DataToAdapterFrom.SQLITE, sortList(savedList));
        } else {
            System.out.println("Using IN APP objects!");
            initializeRadioStationAdapter(DataToAdapterFrom.INAPPUPDATE, sortList(RadioData.getInstance().getRadioInfoList()));
        }
    }

    //If downloading radio objects from firebase, then SQLlite is populated with new data if there has been any changes in firebase.
    private ArrayList<ArrayList<RadioObject>> handleSQLiteList() {
        ArrayList<RadioObject> sortedListFromFirebase = sortList(radioStations);
        ArrayList<RadioObject> sortedSavedFirebaseList = sortList(DatabaseManager.INSTANCE.getAllRadioStations());

        if (sortedSavedFirebaseList.isEmpty()) {
            DatabaseManager.INSTANCE.addRadioStations(sortedListFromFirebase);
        } else {
            if (sortedListFromFirebase.size() == sortedSavedFirebaseList.size()) {
                int index = 0;
                boolean isTheSame = true;
                for (RadioObject radioObject: sortedListFromFirebase) {
                    if (!radioObject.getName().equals(sortedSavedFirebaseList.get(index).getName())
                            && radioObject.getRadioImage() != sortedSavedFirebaseList.get(index).getRadioImage()
                            && !radioObject.getUrl().equals(sortedSavedFirebaseList.get(index).getUrl())) {
                        isTheSame = false;
                        break;
                    }
                    index += 1;
                }
                if (!isTheSame) {
                    reCreateTable();
                }
            } else {
                reCreateTable();
            }
        }
        ArrayList<ArrayList<RadioObject>> sortedLists = new ArrayList<>();
        sortedLists.add(sortedListFromFirebase);
        sortedLists.add(sortedSavedFirebaseList);
        return sortedLists;
    }

    private void reCreateTable() {
        DatabaseManager.INSTANCE.reCreateTable();
        DatabaseManager.INSTANCE.addRadioStations(radioStations);
    }

    private void initializeRecyclerView(View v){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.scrollToPosition(LinearLayoutManager.HORIZONTAL);

        recyclerView = v.findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(radioStationAdapter);

    }

    @Override
    public void onDestroyView() {
        this.recyclerView.setAdapter(null);
        super.onDestroyView();
    }
}
