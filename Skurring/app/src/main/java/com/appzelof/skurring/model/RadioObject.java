package com.appzelof.skurring.model;


import java.util.HashMap;

public class RadioObject {
    private String url;
    private String name;
    private int radioImage;
    private int choosenSpot;


    public RadioObject(String name, String url, int radioImage){
        setName(name);
        setUrl(url);
        setRadioImage(radioImage);
    }

    public RadioObject() {}

    public RadioObject initFromFirebase(HashMap map) {
        setUrl((String) map.get("radioUrl"));
        setName((String) map.get("radioNavn"));
        setFirebaseImage((Long) map.get("androidImageInt"));
        return this;
    }

    public String getUrl() {
        if (this.url != null) {
            return url;
        } else {
            return "";
        }
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        if (this.name != null) {
            return this.name;
        } else {
            return "";
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRadioImage() {
        return this.radioImage;
    }

    public void setFirebaseImage(Long radioImage) {
        this.radioImage = radioImage.intValue();
    }

    public void setRadioImage(int radioImage) {
        this.radioImage = radioImage;
    }

    public void setChoosenSpot(int choosenSpot) {
        this.choosenSpot = choosenSpot;
    }

    public int getChoosenSpot() {
        return this.choosenSpot;
    }

    @Override
    public String toString(){
        return getRadioImage() + getUrl() + getName();
    }

}