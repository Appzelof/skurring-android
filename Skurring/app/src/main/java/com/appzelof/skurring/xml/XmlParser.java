package com.appzelof.skurring.xml;

import android.os.AsyncTask;
import android.util.Log;

import com.appzelof.skurring.Interface.WeatherUpdate;
import com.appzelof.skurring.activityViews.PlayActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by daniel on 21/02/2018.
 */

public class XmlParser extends AsyncTask<String, Void, String> {

    private static final String TAG = "DownloadData";
    private WeatherUpdate weatherUpdate;

    public XmlParser(PlayActivity playerAct) {
        this.weatherUpdate = playerAct;
    }

    @Override
    protected String doInBackground(String... strings) {

        Log.d(TAG, "do in background " + strings[0]);

        String rssFeed = downloadXml(strings[0]);

        if (rssFeed == null) {
            Log.e(TAG, "error downloading");
        }

        return rssFeed;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Log.d(TAG, "onPostExecute: parameter is " + s);
        ParseData parseData = new ParseData();
            weatherUpdate.getUpdatedWeatherData(parseData.parse(s));

        
    }



    private String downloadXml(String string) {

        StringBuilder xmlResult = new StringBuilder();

        try {

            URL url = new URL(string);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(300);
            connection.setReadTimeout(200);

            int response = connection.getResponseCode();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            int charsRead;
            char[] chars = new char[500];
            while (true) {
                charsRead = reader.read(chars);
                if (charsRead < 0) {
                    break;
                }

                if (charsRead > 0) {
                    xmlResult.append(String.copyValueOf(chars, 0, charsRead));
                }
            }

            reader.close();

            return xmlResult.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}

