package com.appzelof.skurring.networkHandler;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.AsyncTask;

import com.appzelof.skurring.Interfaces.ImageDownloaded;

import java.io.InputStream;
import java.net.URL;

public class ImageDownloader extends AsyncTask<Void, Void, Bitmap> implements ImageDownloaded {

    public ImageDownloaded imageDownloaded;
    private String imageUrl;
    private FetchImageJSON fetchImageJSON;

    public void downloadImageFromPureUrl(String pureImageUrl) {
        this.imageUrl = pureImageUrl;
        this.execute();
    }

    public void downloadImageFromParts(String artist, String album) {
        fetchImageJSON = new FetchImageJSON(this, createItunesEndpoint(artist, album));
        fetchImageJSON.execute();
    }

    public void downloadImageFromPart(String part) {
        String[] parts = {"", ""};
        if (part.contains(" - ")) {
            parts = part.split(" - ");
        } else {
            if (part.contains("-")) {
                parts = part.split("-");
            }
        }
        fetchImageJSON = new FetchImageJSON(this, createItunesEndpoint(parts[0], parts[1]));
        fetchImageJSON.execute();
    }

    private String createItunesEndpoint(String artist, String album) {
        final String itunesSongUrl = "https://itunes.apple.com/search?term=" + artist + "+" + album + "&entity=song";
        System.out.println("ITUNES URL : " + itunesSongUrl);
        return "https://itunes.apple.com/search?term=" + artist + "+" + album + "&entity=song";
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        System.out.println("Preparing image download");
    }

    @Override
    protected Bitmap doInBackground(Void... voids) {
        System.out.println("Downloading image");
        Bitmap bitmap = null;
        try {
            InputStream inputStream = new URL(this.imageUrl).openStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (Exception e) {
            System.out.println("IMAGE ERR: " + e.getMessage());
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        this.imageDownloaded.imageDownloaded(bitmap);
    }

    @Override
    public void imageDownloaded(Bitmap imageDownloaded) {
        //Not actually needed, but have to implement.
    }

    @Override
    public void imageJSONURL(String url) {
        this.imageUrl = url;
        this.execute();
    }

    @Override
    public void errorGettingImageFromJSON() {
        this.imageDownloaded.errorGettingImageFromJSON();
    }
}
