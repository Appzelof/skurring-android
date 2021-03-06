package com.appzelof.skurring.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appzelof.skurring.Interfaces.UpdateMainFragmentUI;
import com.appzelof.skurring.R;
import com.appzelof.skurring.SQLite.DatabaseManager;
import com.appzelof.skurring.fragments.MainFragment;
import com.appzelof.skurring.fragments.StationsFragment;
import com.appzelof.skurring.holder.RadioViewHolder;
import com.appzelof.skurring.model.RadioObject;

import java.util.ArrayList;
import java.util.HashMap;

public class RadioStationAdapter extends RecyclerView.Adapter<RadioViewHolder> {
    private ArrayList<RadioObject> radioObjectArrayList;
    public UpdateMainFragmentUI updateMainFragmentUI;

    public RadioStationAdapter(ArrayList<RadioObject> radioObjectArrayList) {
        this.radioObjectArrayList = radioObjectArrayList;
    }

    public void updateData(ArrayList<RadioObject> radioObjectArrayList) {
        this.radioObjectArrayList = radioObjectArrayList;
    }

    @NonNull
    @Override
    public RadioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_radio_card, parent, false);
        return new RadioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RadioViewHolder holder, final int position) {
        holder.updateUI(radioObjectArrayList.get(position).getRadioImage(),radioObjectArrayList.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(position);
                RadioObject tappedRadioStation = radioObjectArrayList.get(position);
                tappedRadioStation.setChoosenSpot(MainFragment.changingRadioChannel);

                if (DatabaseManager.INSTANCE.radioStationDoesntExist()) {
                    DatabaseManager.INSTANCE.addRadioStation(tappedRadioStation);
                    System.out.println("Radio station exists: " + !DatabaseManager.INSTANCE.radioStationDoesntExist());
                } else {

                    if (!DatabaseManager.INSTANCE.updateRadioStation(tappedRadioStation)) {
                        System.out.println("Kunne ikke oppdatere");
                    }
                }
                updateMainFragmentUI.updateUI();
            }
        });
    }

    @Override
    public int getItemCount() {
        return radioObjectArrayList.size();
    }


}
