package com.appzelof.skurring.networkHandler;

import android.os.AsyncTask;


import com.appzelof.skurring.Interfaces.UpdateWeatherUI;
import com.appzelof.skurring.networkHandler.xmlParser.XmlDataParser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class XmlDownloader extends AsyncTask<String, Void, String> {

    private UpdateWeatherUI updateWeatherUI;

    public XmlDownloader(UpdateWeatherUI updateWeatherUI) {
        this.updateWeatherUI = updateWeatherUI;

    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        XmlDataParser xmlDataParser = new XmlDataParser(updateWeatherUI);
        xmlDataParser.parseXmlData(s);
        xmlDataParser.getList();

    }

    @Override
    protected String doInBackground(String... strings) {
        String weatherFeed = downloadXml(strings[0]);
        if (weatherFeed == null){
            System.out.println("downloading");
        }
        return weatherFeed;
    }

    public String downloadXml(String urlPath) {
        StringBuilder xmlFeed = new StringBuilder();

        try {
            URL url = new URL(urlPath);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
              char[] inputBuffer = new char[500];
              int charsRead;
               while (true) {
                   charsRead = reader.read(inputBuffer);
                   if (charsRead < 0) {
                       break;
                   }
                   if (charsRead > 0) {
                       xmlFeed.append(String.valueOf(inputBuffer, 0, charsRead));
                   }
               }
            reader.close();
            return xmlFeed.toString();

        } catch (MalformedURLException e){
            e.getMessage();
        } catch (IOException e){
            e.getMessage();
        }

        return null;
    }


}