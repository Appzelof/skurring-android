package com.appzelof.skurring.SQLiteFirebase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.appzelof.skurring.model.RadioObject;

import java.util.ArrayList;

public class DatabaseAdapter {

    private SQLiteDatabase database;
    private DatabaseHelper databaseHelper;
    private String[] allColumns = {DatabaseHelper.RADIONAME, DatabaseHelper.RADIOIMAGE, DatabaseHelper.RADIOURL};

    public DatabaseAdapter(Context context) {
        this.databaseHelper = new DatabaseHelper(context);
    }

    public void insert(RadioObject radioObject) {
        if (this.database != null) {
            ContentValues contentValues = this.getContentValueFromRadioObject(radioObject);
            database.insert(DatabaseHelper.TABLE_NAME, null, contentValues);
        } else {
            System.out.println("Database is null");
        }
    }

    public void removeRadioObject(RadioObject radioObject) {
        if (database != null) {
            this.database.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper.RADIONAME + " = " + radioObject.getName(),
                    null);
        }
    }

    public ArrayList<RadioObject> getAllSavedFirebaseObjects() {
        ArrayList<RadioObject> savedFirebaseObjects = new ArrayList<>();
        if (database != null) {
            Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, allColumns, null,
                    null, null,
                    null,
                    null);
            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {
                RadioObject radioObject = cursorTiRadioObject(cursor);
                savedFirebaseObjects.add(radioObject);
                cursor.moveToNext();
            }
            cursor.close();
            this.close();
        }
        return savedFirebaseObjects;
    }

    private RadioObject cursorTiRadioObject(Cursor cursor) {
        RadioObject radioObject = new RadioObject();
        radioObject.setName(cursor.getString(cursor.getColumnIndex(DatabaseHelper.RADIONAME)));
        radioObject.setRadioImage(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.RADIOIMAGE)));
        radioObject.setUrl(cursor.getString(cursor.getColumnIndex(DatabaseHelper.RADIOURL)));
        return radioObject;
    }

    private ContentValues getContentValueFromRadioObject(RadioObject radioObject) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.RADIOIMAGE, radioObject.getRadioImage());
        contentValues.put(DatabaseHelper.RADIONAME, radioObject.getName());
        contentValues.put(DatabaseHelper.RADIOURL, radioObject.getUrl());
        return contentValues;
    }

    public void open() throws SQLException {
        this.database = databaseHelper.getWritableDatabase();
    }

    public void close() {
        this.databaseHelper.close();
    }

    public void reCreateTable() {
        this.databaseHelper.onUpgrade(this.database, 1, 1);
    }


}
