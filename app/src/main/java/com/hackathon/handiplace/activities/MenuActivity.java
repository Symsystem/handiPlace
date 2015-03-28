package com.hackathon.handiplace.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.hackathon.handiplace.R;
import com.hackathon.handiplace.classes.Position;
import com.hackathon.handiplace.request.PermissionGPS;

import org.apache.http.util.ExceptionUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class MenuActivity extends ActionBarActivity {

    Toolbar toolbar;

    @InjectView(R.id.restoLocationButton)
    ImageButton mRestoLocation;
    @InjectView(R.id.restoFavorisButton)
    ImageButton mRestoFavoris;
    @InjectView(R.id.typeHandicapButton)
    ImageButton mTypeHandicap;

    private Position mPosition;
    private LocationAsyncTask mLocationAsyncTask;
    private String macAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        login();

        Toast.makeText(this, macAddress, Toast.LENGTH_LONG).show();

        // Vérification de l'activation de la localisation
        mLocationAsyncTask = new LocationAsyncTask(this);
        mLocationAsyncTask.execute();
    }

    private void login() {
        WifiManager manager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = manager.getConnectionInfo();
        macAddress = info.getMacAddress();

    }


    @OnClick (R.id.restoLocationButton)
    public void startRestLocationActivity(View view){
        /*if(!mLocationAsyncTask.isFinished()){
            updateBarHandler = new Handler();
        }*/

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
        private boolean isLocationFinished;

        public LocationAsyncTask(Context context){
            this.context = context;
        }

        @Override
        protected void onPostExecute(Position result) {
            isLocationFinished = true;
            mPosition = result;
        }

        @Override
        protected Position doInBackground(Void... params) {

            /** Récupère le locationManager qui gère la localisation */
            LocationManager locManager;
            locManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            /** Test si le gps est activé ou non */
            if (!locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                /** on lance notre activity (qui est une dialog) */
                Intent localIntent = new Intent(MenuActivity.this, PermissionGPS.class);
                localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(localIntent);
            }

            Location location;
            /** Ensuite on demande a ecouter la localisation (dans la classe qui implémente le LocationListener*/
            if (locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                location = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            } else {
                location = locManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }

            if(location == null){
                return null;
            }
            else {
                return new Position(location.getLatitude(), location.getLongitude());
            }
        }

        @Override
        protected void onPreExecute() {
            //isLocationFinished = false;
        }

        public boolean isFinished(){
            return isLocationFinished;
        }
    }

}
