package com.appzelof.skurring.networkHandler;

import android.content.Context;
import android.os.AsyncTask;


import com.appzelof.skurring.Interfaces.UpdateWeatherInfo;
import com.appzelof.skurring.networkHandler.xmlParser.XmlDataParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class XmlDownloader extends AsyncTask<String, Void, String> {

    private Context context;
    private UpdateWeatherInfo updateWeatherInfo;

    public XmlDownloader(Context context, UpdateWeatherInfo updateWeatherInfo) {
    this.context = context;
    this.updateWeatherInfo = updateWeatherInfo;

    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        XmlDataParser xmlDataParser = new XmlDataParser(context, updateWeatherInfo);
        if (s != null) {
            xmlDataParser.parseXmlData(s);
        } else {
            System.out.println("No correct url");
        }

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