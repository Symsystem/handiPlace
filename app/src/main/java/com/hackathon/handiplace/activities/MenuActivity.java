package com.hackathon.handiplace.activities;

import android.app.AlertDialog;
import android.app.Application;
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
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hackathon.handiplace.HandiPlaceApplication;
import com.hackathon.handiplace.R;
import com.hackathon.handiplace.classes.Config;
import com.hackathon.handiplace.classes.Position;
import com.hackathon.handiplace.request.OkHttpStack;
import com.hackathon.handiplace.request.PermissionGPS;
import com.hackathon.handiplace.request.PostRequest;

import org.apache.http.util.ExceptionUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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

        if(HandiPlaceApplication.user == null)
            login();

        // Vérification de l'activation de la localisation
        mLocationAsyncTask = new LocationAsyncTask(this);
        mLocationAsyncTask.execute();
    }

    private void login() {
        WifiManager manager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = manager.getConnectionInfo();
        macAddress = info.getMacAddress();

        String URL = Config.BASE_URL + "api/users/" + macAddress + ".json";
        StringRequest request = new StringRequest(URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {

                    JSONObject userJson = new JSONObject(s);

                    if (userJson.has("response")) {

                        if (userJson.getBoolean("response")) {
                            int id = userJson.getInt("id");
                        }
                        else {
                            // Renvoie une requête pour créer un compte
                            createUser();
                        }
                    } else {
                        Log.i("Ici non plus ! :", "");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                    new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });

        RequestQueue queue = Volley.newRequestQueue(MenuActivity.this, new OkHttpStack());
        queue.add(request);

    }

    private void createUser(){
        Map<String, String> params = new HashMap<String, String>();
        params.put("macAddress", macAddress);
        String URL = Config.BASE_URL + "apis/users.json";

        PostRequest requestAddUser = new PostRequest(URL, params, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try{
                    JSONObject userJSON = new JSONObject(s);
                    if (userJSON.has("response")) {

                        if (userJSON.getBoolean("response")) {
                            HandiPlaceApplication.user.setId(userJSON.getInt("id"));
                        }
                        else {
                            // Erreur !
                        }
                    } else {

                    }
                }
                catch(JSONException e){
                    e.printStackTrace();
                }
            }
        },
        new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        RequestQueue queue = Volley.newRequestQueue(MenuActivity.this, new OkHttpStack());
        queue.add(requestAddUser);
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
            /*if(result == null){
                ErrorActivity error = new ErrorActivity();
                error.show(getFragmentManager(), "error_dialog");
            }*/
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
