package com.appzelof.skurring.sharePrefsManager;

import android.content.Context;
import android.content.SharedPreferences;

import okhttp3.internal.cache.DiskLruCache;

public class SharePrefsManager {

    private static final String MY_PREFS_NAME = "WeatherInfo";
    private SharedPreferences.Editor editor;
    private SharedPreferences preferences;

    public SharePrefsManager(Context context) {
        preferences = context.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public void saveString(String key, String value){
        editor.putString(key, value);
        editor.commit();
    }

    public void saveInt(String key, int value){
        editor.putInt(key, value);
        editor.commit();
    }

    public String getString(String key){
        String info = preferences.getString(key, null);

        return info;
    }

    public int getInt(String key){
        int info = preferences.getInt( key, 0);


        return info;
    }

}
