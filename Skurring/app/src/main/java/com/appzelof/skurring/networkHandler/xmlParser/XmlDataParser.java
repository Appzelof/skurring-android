package com.appzelof.skurring.networkHandler.xmlParser;

import com.appzelof.skurring.Interfaces.UpdateWeatherUI;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;

public class XmlDataParser {

    private ArrayList<String> tempArrayList;
    private ArrayList<String> iconArrayList;
    private UpdateWeatherUI updateWeatherUI;
    private HashMap<String, String> test;

    public XmlDataParser(UpdateWeatherUI updateWeatherUI) {
        tempArrayList = new ArrayList<>();
        this.updateWeatherUI = updateWeatherUI;
        HashMap<String, String> test = new HashMap<>();
    }

    public void parseXmlData(String xmlData) {
        Boolean inEntry = false;
        String textValue;
        String temp = null;
        String icon = null;

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
                                //icon = xpp.getAttributeValue(3);
                            } else if ("temperature".equalsIgnoreCase(tagName)) {
                                if (tagName != null) {
                                    temp = xpp.getAttributeValue(1);
                                    test.put(tagName, tagName);
                                }

                            }
                            tempArrayList.add(temp);

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

    public void getList(){
        updateWeatherUI.getTempArray(tempArrayList);
    }
}
