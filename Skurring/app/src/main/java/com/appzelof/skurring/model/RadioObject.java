package com.appzelof.skurring.model;


public class RadioObject {
    private String url;
    private String name;
    private int radioImage;


    public RadioObject(String name, String url, int radioImage){
        setName(name);
        setUrl(url);
        setRadioImage(radioImage);


    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRadioImage() {
        return radioImage;
    }

    public void setRadioImage(int radioImage) {
        this.radioImage = radioImage;
    }

    @Override
    public String toString(){
        return radioImage + url + radioImage;
    }

}