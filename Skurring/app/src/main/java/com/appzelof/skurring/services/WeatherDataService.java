package com.appzelof.skurring.services;

import com.appzelof.skurring.model.WeatherObject;

import java.util.ArrayList;

/**
 * Created by daniel on 13/02/2018.
 */

public class WeatherDataService {
    private static final WeatherDataService ourInstance = new WeatherDataService();

    private static String weatherURL = "http://www.yr.no/sted/Norg/Oslo/Oslo/Kalbakken/varsel.xml";

    private ArrayList<WeatherObject> weatherObjectArrayList;

    public static WeatherDataService getInstance() {
        return ourInstance;
    }

    private WeatherDataService() {

    }

    private ArrayList<WeatherObject> getWeatherObjectArrayList() {




        return weatherObjectArrayList;
    }


}
