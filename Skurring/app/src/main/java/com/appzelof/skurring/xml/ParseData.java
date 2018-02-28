package com.appzelof.skurring.xml;

import android.widget.ImageView;
import android.widget.TextView;

import com.appzelof.skurring.model.WeatherObject;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.WeakHashMap;

/**
 * Created by daniel on 27/02/2018.
 */

public class ParseData {

    private static final String TAG = "ParseData";
    private ArrayList<WeatherObject> weatherObjectArrayList;
    private WeatherObject weatherObject;
    public static String img;
    public static String temp;

    public ParseData() {
        weatherObjectArrayList = new ArrayList<>();
    }

    public  ArrayList<WeatherObject> getWeatherObjectArrayList() {
        return weatherObjectArrayList;
    }

    public boolean parse(String xmlData) {
        boolean status = true;
        boolean inTabular = false;
        String textValue = "";
        weatherObject = new WeatherObject();


        try {

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();
            factory.setNamespaceAware(true);
            xpp.setInput(new StringReader(xmlData));

            int eventType = xpp.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagName = xpp.getName();
                    switch (eventType) {
                        case XmlPullParser.START_TAG:
                             break;

                        case XmlPullParser.TEXT:
                            textValue = xpp.getText();
                            break;

                        case XmlPullParser.END_TAG:
                                if ("time".equalsIgnoreCase(tagName)) {
                                    weatherObjectArrayList.add(weatherObject);
                                } else if ("symbol".equalsIgnoreCase(tagName)) {
                                  String image = (xpp.getAttributeValue(null, "var"));
                                  if (image != null) {
                                      setImg(image);
                                  }
                                } else if ("temperature".equalsIgnoreCase(tagName)) {
                                    String temp = (xpp.getAttributeValue(null, "value"));
                                        setTemp(temp);
                                }

                            break;

                        default:

                    }

                    eventType = xpp.next();

                }

            } catch (Exception e) {
             status = false;
            e.printStackTrace();
        }

       return status;
    }

    public static String getImg() {
        return img;
    }

    public static void setImg(String img) {
        ParseData.img = img;
    }

    public static String getTemp() {
        return temp;
    }

    public static void setTemp(String temp) {
        ParseData.temp = temp;

    }
}
