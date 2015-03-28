package com.hackathon.handiplace.request;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.widget.Toast;

import com.hackathon.handiplace.classes.Position;

/**
 * Created by sym on 28/03/15.
 */
public class LocationAsyncTask extends AsyncTask<Void, Void, Position> {

    private Context context;

    public LocationAsyncTask(Context context){
        this.context = context;
    }

    @Override
    protected void onPostExecute(Position result) {
        Toast.makeText(context, "POSITION GET!", Toast.LENGTH_LONG).show();
    }

    @Override
    protected Position doInBackground(Void... params) {

        // Acquire a reference to the system Location Manager
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_LOW);

        // Define a listener that responds to location updates
        String provider = locationManager.getBestProvider(criteria, true);
        Location location =locationManager.getLastKnownLocation(provider);

        //locationManager.requestLocationUpdates(provider, 1000, 10, locationListener);

        return new Position(location.getLatitude(), location.getLongitude());
    }

    @Override
    protected void onPreExecute() {

    }
}
