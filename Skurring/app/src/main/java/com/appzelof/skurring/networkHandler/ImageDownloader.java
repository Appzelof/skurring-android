package com.appzelof.skurring.networkHandler;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.AsyncTask;

import com.appzelof.skurring.Interfaces.ImageDownloaded;

import java.io.InputStream;
import java.net.URL;

public class ImageDownloader extends AsyncTask<Void, Void, Bitmap> {

    public ImageDownloaded imageDownloaded;
    private String imageUrl;

    public void downloadImageFromPureUrl(String pureImageUrl) {
        this.imageUrl = pureImageUrl;
        this.execute();
    }

    public void downloadImageFromParts(String artist, String album) {
        getImageJSON(artist, album);
    }

    private void getImageJSON(String artist, String album) {
        String imageUrlFromJSON = "";

    }
    private Bitmap getRezizedBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        float scaleWidth = ((float) 50) / width;
        float scaleHeight = ((float) 50) / width;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, false);
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
        this.imageDownloaded.imageDownloaded(this.getRezizedBitmap(bitmap));
    }
}
