package com.appzelof.skurring.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.appzelof.skurring.fragments.MainFragment;
import com.appzelof.skurring.model.RadioObject;

import java.util.ArrayList;

public class DatabaseAdapter {

    private SQLiteDatabase database;
    private DatabaseHelper databaseHelper;
    private String[] allColumns = {DatabaseHelper.RADIONAME, DatabaseHelper.RADIOIMAGE, DatabaseHelper.RADIOURL, DatabaseHelper.CHOSENRADIOSPOT};

    public DatabaseAdapter(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public void insert(RadioObject radioObject) {
        if (database != null) {
            ContentValues radioStationValue = this.getContentValueFromRadioObject(radioObject);
            database.insert(DatabaseHelper.TABLE_NAME, null, radioStationValue);
            this.close();
        } else {
            System.out.println("Database is null");
        }
    }

    public boolean update(RadioObject radioObject) {
        boolean updatedSuccess = false;
        if (database != null) {
            updatedSuccess = database.update(DatabaseHelper.TABLE_NAME, getContentValueFromRadioObject(radioObject), DatabaseHelper.CHOSENRADIOSPOT + " = " + radioObject.getChoosenSpot(), null)
                    > 0;
            database.close();
        }
        return updatedSuccess;
    }

    public void removeRadioObject(RadioObject radioObject) {
        if (database != null) {
            this.database.delete(DatabaseHelper.TABLE_NAME,
                    DatabaseHelper.CHOSENRADIOSPOT + " = " + radioObject.getChoosenSpot(),
                    null);
            this.close();
        }
    }

    public ArrayList<RadioObject> getAllSavedRadioStations() {
        ArrayList<RadioObject> savedRadioStations = new ArrayList<>();
        if (database != null) {
            Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, allColumns,
                    null,
                    null,
                    null,
                    null,
                    null);
            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {
                RadioObject radioObject = cursorToRadioObject(cursor);
                savedRadioStations.add(radioObject);
                cursor.moveToNext();
            }
            cursor.close();
            this.close();
        }
        return savedRadioStations;
    }

    public boolean radioStationDoesntExists() {
        int cursorCount = 1;
        if (database != null) {
            String[] args = {"" + MainFragment.changingRadioChannel};
            String whereSql = DatabaseHelper.CHOSENRADIOSPOT + " = ?";
            Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, allColumns, whereSql, args, null, null, null);
            cursorCount = cursor.getCount();
            cursor.close();
            this.close();
        }
        return cursorCount == 0;
    }

    private RadioObject cursorToRadioObject(Cursor cursor) {
        RadioObject radioObject = new RadioObject();
        radioObject.setName(cursor.getString(cursor.getColumnIndex(DatabaseHelper.RADIONAME)));
        radioObject.setRadioImage(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.RADIOIMAGE)));
        radioObject.setUrl(cursor.getString(cursor.getColumnIndex(DatabaseHelper.RADIOURL)));
        radioObject.setChoosenSpot(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.CHOSENRADIOSPOT)));
        return radioObject;
    }

    private ContentValues getContentValueFromRadioObject(RadioObject radioObject) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.RADIOIMAGE, radioObject.getRadioImage());
        contentValues.put(DatabaseHelper.RADIONAME, radioObject.getName());
        contentValues.put(DatabaseHelper.RADIOURL, radioObject.getUrl());
        contentValues.put(DatabaseHelper.CHOSENRADIOSPOT, radioObject.getChoosenSpot());
        return contentValues;
    }

    public void open() throws SQLException {
        this.database = databaseHelper.getWritableDatabase();
    }

    private void close() {
        this.databaseHelper.close();
    }


}
