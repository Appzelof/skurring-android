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
import java.util.List;
import java.util.Locale;

/**
 * Created by daniel on 21/02/2018.
 */

public class XmlParser extends AsyncTask<Object, String, Integer> {



    public XmlParser()  {


    }

    @Override
    protected Integer doInBackground(Object... objects) {


        return null;
    }

}
