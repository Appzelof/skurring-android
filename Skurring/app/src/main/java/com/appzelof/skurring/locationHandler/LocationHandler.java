package com.appzelof.skurring.locationHandler;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.appzelof.skurring.Interfaces.UpdatePlayerUI;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;


public class LocationHandler {

    private Context context;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationManager locationManager;
    private UpdatePlayerUI updatePlayerUI;


    public LocationHandler(Context context, UpdatePlayerUI updatePlayerUI) {
        this.updatePlayerUI = updatePlayerUI;
        this.context = context;
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    public void getLastKnownLocation(Activity activity){
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(activity, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
        } else {
                fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        getLocation(location.getLatitude(), location.getLongitude(), "");
                    }
                });

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    float speed = location.getSpeed() * 3600/1000;
                    DecimalFormat decimalFormat = new DecimalFormat("###.#");
                    getLocation(location.getLatitude(), location.getLongitude(), decimalFormat.format(speed));


                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            });

        }


    }

    private void getLocation(Double lat, Double lon, String speed) {
        try {
            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(lat, lon, 1);
            String adminArea = addresses.get(0).getAdminArea();
            String locality = addresses.get(0).getLocality();
            String country = addresses.get(0).getCountryName();
            String subLocality = addresses.get(0).getSubLocality();

            System.out.println("###################################");

            updatePlayerUI.updateLocationInfo(country, adminArea, locality, subLocality);
            updatePlayerUI.updateSpeedInfo(speed);


        } catch (IOException e) {
            e.getMessage();
        }

    }

}
