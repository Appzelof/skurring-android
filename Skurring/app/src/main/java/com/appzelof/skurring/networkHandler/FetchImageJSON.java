package com.appzelof.skurring.networkHandler;

import android.os.AsyncTask;

import com.appzelof.skurring.Interfaces.ImageDownloaded;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class FetchImageJSON extends AsyncTask<Void, Void, StringBuilder> {

    private ImageDownloaded imageDownloaded;
    private String apiEndPoint;

    public FetchImageJSON(ImageDownloader imageDownloader, String apiEndPoint) {
        imageDownloaded = imageDownloader;
        this.apiEndPoint = apiEndPoint;
    }

    @Override
    protected StringBuilder doInBackground(Void... voids) {
        StringBuilder JSONString = new StringBuilder();
        try {
            URL url = new URL(this.apiEndPoint);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while(line != null) {
                line = bufferedReader.readLine();
                JSONString.append(line);
            }
        } catch (MalformedURLException urlEx) {
            System.out.println("Failed URL: " + urlEx.getMessage());
        } catch (IOException io) {
            System.out.println("Failed io: " + io.getMessage());
        }
        return JSONString;
    }

    @Override
    protected void onPostExecute(StringBuilder s) {
        super.onPostExecute(s);
        try {
            JSONObject itunesObject = new JSONObject(s.toString());
            parseJSON(itunesObject);
        } catch (JSONException jsException) {
            System.out.println("FAILED JSON: " + jsException.getMessage());
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
    }

    private void parseJSON(JSONObject jsonObject) {
        try {
            if (jsonObject.get("results") != null) {
                JSONArray results = (JSONArray) jsonObject.get("results");
                if (results.length() > 0) {
                    JSONObject firstPossibleAlbum = results.getJSONObject(0);
                    if (firstPossibleAlbum.get("artworkUrl100") != null) {
                        System.out.println("IMAGE URL : " + firstPossibleAlbum.get("artworkUrl100").toString());
                        String imageUrl = (String) firstPossibleAlbum.get("artworkUrl100");
                        String[] urlParts = imageUrl.split("/source/");
                        if (urlParts.length > 1) {
                            String finalString = urlParts[0] + "/source/1000x1000bb.jpg";
                            this.imageDownloaded.imageJSONURL(finalString);
                        }

                    }
                } else {
                    //Error
                    this.imageDownloaded.errorGettingImageFromJSON();
                }
            }
        } catch (Exception e) {
            System.out.println("ERR: " + e.getMessage());
        }
    }

}
