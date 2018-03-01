package com.appzelof.skurring.model;

/**
 * Created by daniel on 13/02/2018.
 */

public class WeatherObject {

    private String temp;
    private String imageName;

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public WeatherObject(String weatherIcon, String weatherTemp) {
        setImage(weatherIcon);
        setTemp(weatherTemp);
    }

    public WeatherObject(){}

    public void setImage(String image) {
        this.imageName = image;
    }

    public String getTemp() {

        if (temp == null) {
            temp = "";
            return temp;
        } else {
            return temp;
        }
    }

    public String getImage() {

        if (imageName == null) {
             imageName = "";
             return imageName;
        } else {
            return imageName;
        }
    }
}

