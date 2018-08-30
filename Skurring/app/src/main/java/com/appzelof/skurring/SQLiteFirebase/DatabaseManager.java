package com.appzelof.skurring.SQLiteFirebase;

import android.content.Context;

import com.appzelof.skurring.model.RadioObject;

import java.util.ArrayList;

public class DatabaseManager {

    public static DatabaseManager INSTANCE;
    private DatabaseAdapter databaseAdapter;

    public DatabaseManager(Context context) {
        databaseAdapter = new DatabaseAdapter(context);
    }

    public void reCreateTable() {
        this.databaseAdapter.open();
        databaseAdapter.reCreateTable();
        System.out.println("Station table was dropped and recreated!");
        this.databaseAdapter.close();
    }

    public void addRadioStations(ArrayList<RadioObject> radioList) {
        this.databaseAdapter.open();
        for (RadioObject radioObject: radioList) {
            this.databaseAdapter.insert(radioObject);
        }
        System.out.println("Stations was added!");
        this.databaseAdapter.close();
    }

    public ArrayList<RadioObject> getAllRadioStations() {
        this.databaseAdapter.open();
        return this.databaseAdapter.getAllSavedFirebaseObjects();
    }

}
