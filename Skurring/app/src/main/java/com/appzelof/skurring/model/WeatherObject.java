package com.appzelof.skurring.model;

/**
 * Created by daniel on 13/02/2018.
 */

public class WeatherObject {

    private String city;
    private String temp;
    private int image;

    public WeatherObject(String city, String temp, int image) {
        this.city = city;
        this.temp = temp;
        this.image = image;
    }

    public String getCity() {
        return city;
    }

    public String getTemp() {
        return temp;
    }

    public int getImage() {
        return image;
    }
}
