package com.appzelof.skurring.networkHandler.xmlParser;

import android.content.Context;

import com.appzelof.skurring.Interfaces.UpdateLocationInfo;
import com.appzelof.skurring.Interfaces.UpdateWeatherInfo;
import com.appzelof.skurring.sharePrefsManager.SharePrefsManager;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

public class XmlDataParser {

    private ArrayList<String> tempArrayList;
    private ArrayList<String> iconArrayList;
    private SharePrefsManager sharePrefsManager;
    private UpdateWeatherInfo updateWeatherInfo;


    public XmlDataParser(Context context, UpdateWeatherInfo updateWeatherInfo) {
        tempArrayList = new ArrayList<>();
        iconArrayList = new ArrayList<>();
        sharePrefsManager = new SharePrefsManager(context);
        this.updateWeatherInfo = updateWeatherInfo;
    }

    public void parseXmlData(String xmlData) {
        Boolean inEntry = false;
        String temp;
        String icon;

        try {
            XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
            xmlPullParserFactory.setNamespaceAware(true);
            XmlPullParser xpp = xmlPullParserFactory.newPullParser();
            xpp.setInput(new StringReader(xmlData));
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT){
                String tagName = xpp.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if ("weatherdata".equalsIgnoreCase(tagName)) {
                            inEntry = true;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (inEntry) {
                            if ("symbol".equalsIgnoreCase(tagName)) {
                                if (xpp.getAttributeCount() == 4) {
                                    icon = xpp.getAttributeValue(3);
                                    iconArrayList.add(icon);
                                    sharePrefsManager.saveString("symbol", (iconArrayList.get(0)));
                                    updateWeatherInfo.parsed(true);
                                }
                            } else if ("temperature".equalsIgnoreCase(tagName)) {
                                if (xpp.getAttributeCount() == 2) {
                                    temp = xpp.getAttributeValue(1);
                                    tempArrayList.add(temp);
                                    sharePrefsManager.saveString("temp", tempArrayList.get(0));
                                    updateWeatherInfo.parsed(true);
                                }
                            }

                            break;
                        }
                    default:
                }

                eventType = xpp.next();

            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
