package com.appzelof.skurring.xml;

import com.appzelof.skurring.model.WeatherObject;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;

/**
 * Created by daniel on 27/02/2018.
 */

public class ParseData {

    private static final String TAG = "ParseData";
    public static String img;
    public static String temp;

    private ArrayList<String> weatherSymbol;
    private ArrayList<String> weatherTemperature;

    public ParseData() {
        weatherSymbol = new ArrayList<String>();
        weatherTemperature = new ArrayList<String>();
    }


    public WeatherObject parse(String xmlData) {
        boolean status = true;
        boolean inTabular = false;
        String textValue = "";


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

                        case XmlPullParser.TEXT:
                            textValue = xpp.getText();
                            break;

                        case XmlPullParser.END_TAG:

                            switch (tagName) {

                                case "symbol":
                                    String image = (xpp.getAttributeValue(null, "var"));
                                    if (image != null) {
                                        this.weatherSymbol.add(image);
                                        System.out.println(image);
                                    }
                                    break;

                                case "temperature":
                                    String temp = (xpp.getAttributeValue(null, "value"));
                                    if (temp != null) {
                                        this.weatherTemperature.add(temp);
                                        System.out.println(temp);
                                    }
                                    break;
                            }

                            break;

                        default:
                            break;
                    }

                eventType = xpp.next();


                }

            } catch (Exception e) {
             status = false;
            e.printStackTrace();
        }

       return getWeatherObject();
    }

    public WeatherObject getWeatherObject() {
        WeatherObject theWeather = new WeatherObject();
        if (this.weatherTemperature.size() > 0 && this.weatherSymbol.size() > 0) {
            theWeather.setTemp(this.weatherTemperature.get(0));
            theWeather.setImage(this.weatherSymbol.get(0));
        }
        return theWeather;
    }

}
