package com.appzelof.skurring.services;

/**
 * Created by daniel on 13/02/2018.
 */

public class WeatherDataService {
    private static final WeatherDataService ourInstance = new WeatherDataService();

    private static String weatherURL = "http://www.yr.no/sted/Norg/Oslo/Oslo/Kalbakken/varsel.xml";

    public static WeatherDataService getInstance() {
        return ourInstance;
    }

    private WeatherDataService() {

    }



}
