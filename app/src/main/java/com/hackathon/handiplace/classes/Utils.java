package com.hackathon.handiplace.classes;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;

import com.hackathon.handiplace.HandiPlaceApplication;
import com.hackathon.handiplace.activities.InternetActivity;
import com.hackathon.handiplace.activities.LocationActivity;

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

    public static Position getLocation(Context context){
        LocationManager locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        return new Position(location.getLatitude(), location.getLongitude());
    }

    public static Position checkConnectionsReturnLocation (Context context){
        if(!Utils.isNetworkAvailable(context))
        {
            Intent intent = new Intent(context, InternetActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(intent);
        }

        Position pos = new Position(0, 0);
        if(!Utils.isLocationEnabled(context) && !HandiPlaceApplication.isLocated){
            Intent intent = new Intent(context, LocationActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(intent);
        }
        else if(!HandiPlaceApplication.isLocated){
            pos = getLocation(context);
            if(pos != null){
                HandiPlaceApplication.isLocated = true;
            }
        }
        return pos;
    }


}
