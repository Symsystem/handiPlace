package com.hackathon.handiplace.classes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import com.hackathon.handiplace.HandiPlaceApplication;
import com.hackathon.handiplace.R;
import com.hackathon.handiplace.activities.InternetActivity;

import java.util.HashMap;

public class Utils {

    public static String BASE_URL = "http://hackathon.bewweb.be/";

    public static HashMap<Integer, Integer> idHandicap = new HashMap<Integer, Integer>();



    static{
        idHandicap.put(1, 0);
        idHandicap.put(2, 2);
        idHandicap.put(3, 3);
        idHandicap.put(4, 4);
        idHandicap.put(5, 5);
        idHandicap.put(6, 1);
    }


    /*public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }

        return isAvailable;
    }
*/
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity =(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivity == null) {
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
            }

            return locationMode != Settings.Secure.LOCATION_MODE_OFF;

        }else{
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }

    }

    public static void updateLocation(Context context){
        /*LocationManager locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        return new Position(location.getLatitude(), location.getLongitude());*/
            LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            // Define a listener that responds to location updates
            LocationListener locationListener = new LocationListener() {
                public void onLocationChanged(Location location) {
                    // Called when a new location is found by the network location provider.
                    Log.d("bwb", "changed Loc : " + location.getLongitude() + ":" + location.getLatitude());
                    HandiPlaceApplication.currentPosition.setLatitude(location.getLatitude());
                    HandiPlaceApplication.currentPosition.setLatitude(location.getLongitude());
                }

                public void onStatusChanged(String provider, int status, Bundle extras) {
                }

                public void onProviderEnabled(String provider) {
                }

                public void onProviderDisabled(String provider) {
                }
            };

            // getting GPS status
            Boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            // check if GPS enabled
            if (isGPSEnabled) {

                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                if (location != null) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                    Log.d("bwb", "changed Loc : " + location.getLongitude() + ":" + location.getLatitude());
                    HandiPlaceApplication.currentPosition.setLatitude(location.getLatitude());
                    HandiPlaceApplication.currentPosition.setLatitude(location.getLongitude());

                } else {
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                    if (location != null) {
                        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
                        Log.d("bwb", "changed Loc : " + location.getLongitude() + ":" + location.getLatitude());
                        HandiPlaceApplication.currentPosition.setLatitude(location.getLatitude());
                        HandiPlaceApplication.currentPosition.setLatitude(location.getLongitude());
                    }
                }
            }
    }

    public static void checkConnectionsLocation (Context context){
        if(!Utils.isNetworkAvailable(context))
        {
            Intent intent = new Intent(context, InternetActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(intent);

            ((Activity) context).overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_top);
        }
        updateLocation(context);
    }
}
