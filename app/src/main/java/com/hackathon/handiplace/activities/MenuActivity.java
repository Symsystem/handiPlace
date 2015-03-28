package com.hackathon.handiplace.activities;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.hackathon.handiplace.R;
import com.hackathon.handiplace.classes.Position;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class MenuActivity extends ActionBarActivity {

    Toolbar toolbar;

    @InjectView(R.id.restoLocationButton) Button mRestoLocation;
    @InjectView(R.id.restoListButton) Button mRestoList;
    @InjectView(R.id.restoFavorisButton) Button mRestoFavoris;
    @InjectView(R.id.typeHandicapButton) Button mTypeHandicap;

    private Position mPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Position currentPosition;
        LocationAsyncTask loc = new LocationAsyncTask(this);
        loc.execute();
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

    public class LocationAsyncTask extends AsyncTask<Void, Void, Position> {

        private Context context;

        public LocationAsyncTask(Context context){
            this.context = context;
        }

        @Override
        protected void onPostExecute(Position result) {
            Toast.makeText(context, "Position GOT !", Toast.LENGTH_LONG).show();
            mPosition = result;
        }

        @Override
        protected Position doInBackground(Void... params) {

            // Acquire a reference to the system Location Manager
            LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            //locationManager.requestLocationUpdates(provider, 1000, 10, locationListener);

            return new Position(location.getLatitude(), location.getLongitude());
        }

        @Override
        protected void onPreExecute() {

        }


    }

}
