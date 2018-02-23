package com.appzelof.skurring.xml;

import android.app.Application;
import android.content.Context;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.util.Xml;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.appzelof.skurring.activityViews.MainActivity;
import com.appzelof.skurring.activityViews.PlayActivity;
import com.google.android.gms.ads.internal.gmsg.HttpClient;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.Locale;

/**
 * Created by daniel on 21/02/2018.
 */

public class XmlParser extends AsyncTask {

    private static String temp;
    private static String weatherImg;
    private XmlPullParserFactory xmlPullParserFactory;
    private String urlString;

    public XmlParser(String urlString) {
        this.urlString = urlString;
    }

    @Override
    protected Object doInBackground(Object[] objects) {

        try {

            URL url = new URL(this.urlString);
            HttpURLConnection connect = (HttpURLConnection) url.openConnection();
            connect.setReadTimeout(10000);
            connect.setConnectTimeout(14000);
            connect.setRequestMethod("GET");
            connect.setDoInput(true);
            connect.connect();

            InputStream iStream = connect.getInputStream();

            xmlPullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser mParser = xmlPullParserFactory.newPullParser();

            mParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            mParser.setInput(iStream, null);
            mParser.nextTag();
            parseXml(mParser);
            iStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e){
            e.printStackTrace();
        }

        return null;
    }

    public static String getTemperature() {
        return temp;
    }

    public static String getWeatherImg() {
        return weatherImg;
    }

    public void parseXml(XmlPullParser myParser) throws IOException, XmlPullParserException {

        int event;
        String text = null;

        try {
            event = myParser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {
                String name = myParser.getName();
                switch (event) {
                    case XmlPullParser.START_TAG:
                        if (name.equals("temperature")) {
                            temp = myParser.getAttributeValue(null, "value");
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (name.equals("symbol")) {
                            weatherImg = myParser.getAttributeValue(null, "var");
                        }
                }

                event = myParser.next();
            }

            System.out.println(getTemperature() + getWeatherImg() + " været");



        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
