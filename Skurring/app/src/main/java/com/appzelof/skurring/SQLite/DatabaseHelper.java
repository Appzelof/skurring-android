package com.appzelof.skurring.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "RadioStations";
    public static final String RADIONAME = "radioName";
    public static final String RADIOIMAGE = "radioImage";
    public static final String RADIOURL = "radioUrl";
    public static final String CHOOSENRADIOSPOT = "choosenRadioSpot";

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "RadioStations.db";

    private static final String DATABASE_CREATE = "create table " + TABLE_NAME + "(" + RADIONAME +
            " varchar(255) not null, " + RADIOIMAGE +
            " int not null, " + RADIOURL +
            " varchar(255) not null, " + CHOOSENRADIOSPOT + " int not null);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
