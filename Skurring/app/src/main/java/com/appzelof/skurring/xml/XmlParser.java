package com.appzelof.skurring.xml;

import android.app.Application;
import android.content.Context;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.util.Xml;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.appzelof.skurring.activityViews.MainActivity;
import com.appzelof.skurring.activityViews.PlayActivity;
import com.appzelof.skurring.model.WeatherObject;
import com.google.android.gms.ads.internal.gmsg.HttpClient;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by daniel on 21/02/2018.
 */

public class XmlParser extends AsyncTask<String, Void, String> {

    private static final String TAG = "DownloadData";


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
        parseData.parse(s);

        
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
            char[] chars = new char[2000];
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

