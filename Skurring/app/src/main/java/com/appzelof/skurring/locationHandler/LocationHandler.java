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
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import com.appzelof.skurring.Interfaces.UpdateLocationInfo;
import com.appzelof.skurring.activities.MainActivity;
import com.appzelof.skurring.sharePrefsManager.SharePrefsManager;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;


public class LocationHandler {

    private Context context;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationManager locationManager;
    private UpdateLocationInfo updateLocationInfo;
    private Geocoder geocoder;
    private SharePrefsManager sharePrefsManager;

    public LocationHandler(Context context, UpdateLocationInfo updateLocationInfo) {
        this.updateLocationInfo = updateLocationInfo;
        this.context = context;
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        geocoder = new Geocoder(context, Locale.getDefault());
        sharePrefsManager = new SharePrefsManager(context);
    }

    public void getLastKnownLocation(final Activity activity){
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(activity, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
        } else {
                fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        getLocationByGoogle(location.getLatitude(), location.getLongitude());

                    }
                });

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    float speed = location.getSpeed() * 3600/1000;
                    DecimalFormat decimalFormat = new DecimalFormat("###.#");
                    updateLocationInfo.updateSpeedInfo(decimalFormat.format(speed));
                    getSubAdminArea(location.getLatitude(), location.getLongitude());

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

    private void getSubAdminArea(Double lat, Double lon){
        try {

                List<Address> addresses = geocoder.getFromLocation(lat, lon, 1);
                String subAdminArea = addresses.get(0).getSubAdminArea();
                updateLocationInfo.updateAdminArea(subAdminArea);


        } catch (IOException e){
            e.getMessage();
        }
    }


    private void getLocationByGoogle(Double lat, Double lon) {
        try {

            List<Address> addresses = geocoder.getFromLocation(lat, lon, 1);
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String subAdminArea = addresses.get(0).getSubAdminArea();
            updateLocationInfo.updateLocationInfo(country, subAdminArea, postalCode);
            sharePrefsManager.saveString("city", subAdminArea);

        } catch (IOException e) {
            e.getMessage();
        }

    }

}
