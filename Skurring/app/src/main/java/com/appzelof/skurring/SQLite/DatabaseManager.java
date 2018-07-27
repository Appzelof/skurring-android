package com.appzelof.skurring.SQLite;

import android.content.Context;

import com.appzelof.skurring.model.RadioObject;

import java.util.ArrayList;

public class DatabaseManager {

    /* Databasen blir lukket i hver av metodene */

    public static DatabaseManager INSTANCE;
    private DatabaseAdapter databaseAdapter;

    public DatabaseManager(Context context) {
        databaseAdapter = new DatabaseAdapter(context);
    }

    /* Fjerner radiostasjonen fra databasen
     * Brukes ikke, men kan være nyttig */

    public void removeRadioStation(RadioObject radioObject) {
        this.databaseAdapter.open();
        this.databaseAdapter.removeRadioObject(radioObject);
    }

    /* Legger til en ny radiostasjon til databasen */

    public void addRadioStation(RadioObject radioObject) {
        this.databaseAdapter.open();
        this.databaseAdapter.insert(radioObject);
    }

    /* Sjekker om radiostasjon eksisterer i databasen */

    public boolean radioStationDoesntExist() {
        this.databaseAdapter.open();
        return this.databaseAdapter.radioStationDoesntExists();
    }

    /* Henter du alle radiostasjonene i databasen */

    public ArrayList<RadioObject> getAllSavedStations() {
        this.databaseAdapter.open();
        return this.databaseAdapter.getAllSavedRadioStations();
    }

    /* Oppdaterer en radiostasjon */

    public boolean updateRadioStation(RadioObject radioObject) {
        this.databaseAdapter.open();
        return this.databaseAdapter.update(radioObject);
    }
}
