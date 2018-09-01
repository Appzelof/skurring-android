package com.appzelof.skurring.model;

import org.json.JSONObject;

public class PotentialMetadataJSONObject {
    private String imageUrl, title, artist;

    public PotentialMetadataJSONObject(JSONObject jsonObject) {

        try {
            setTitle((String) jsonObject.get("title"));
        } catch (Exception e) {
            System.out.println("Could not get any info from json");
        }

        try {
            setArtist((String) jsonObject.get("artist"));
        } catch (Exception e) {
            System.out.println("Could not get artist");
        }

        try {
            setArtist((String) jsonObject.get("subTitle"));
        } catch (Exception e) {
            System.out.println("Could not get subtitle AKA artist");
        }

        try {
            setImageUrl((String)jsonObject.get("imageUrl"));
        } catch (Exception e) {
            System.out.println("ERROR getting image");
        }

    }

    private void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    private void setTitle(String title) {
        this.title = title;
    }

    private void setArtist(String artist) {
        this.artist = artist;
    }

    public String getImageUrl() {
        if (this.imageUrl != null) {
            return this.imageUrl;
        } else {
            return "";
        }
    }

    public String getTitle() {
        if (this.title != null) {
            return this.title;
        } else {
            return "";
        }
    }

    public String getArtist() {
        if (this.artist != null) {
            return this.artist;
        } else {
            return "";
        }
    }
}
