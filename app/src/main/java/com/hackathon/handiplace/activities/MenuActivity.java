package com.hackathon.handiplace.activities;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.hackathon.handiplace.R;
import com.hackathon.handiplace.classes.Position;
import com.hackathon.handiplace.request.GPSTracker;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class MenuActivity extends ActionBarActivity {

    Toolbar toolbar;

    @InjectView(R.id.restoLocationButton) Button mRestoLocation;
    @InjectView(R.id.restoListButton) Button mRestoList;
    @InjectView(R.id.restoFavorisButton) Button mRestoFavoris;
    @InjectView(R.id.typeHandicapButton) Button mTypeHandicap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Position currentPosition = getCurrentLocation();
    }

    private Position getCurrentLocation() {
        // Acquire a reference to the system Location Manager
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        // Define a listener that responds to location updates
        GPSTracker locationListener = new GPSTracker();

        // Register the listener with the Location Manager to receive location updates
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000L, 500.0f, locationListener);

        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        return new Position(location.getLatitude(), location.getLongitude());
    }

    @OnClick (R.id.restoLocationButton)
    public void startRestLocationActivity(View view){
        Intent intent = new Intent(this, RestoListActivity.class);
        startActivity(intent);
    }

    @OnClick (R.id.typeHandicapButton)
    public void startDisabledTypeActivity(View view){
        Intent intent = new Intent(this, DisabledTypeActivity.class);
        startActivity(intent);
    }

}
