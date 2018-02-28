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

    public void setImage(String image) {
        this.imageName = image;
    }

    public String getTemp() {
        return temp;
    }

    public String getImage() {
        return imageName;
    }
}
