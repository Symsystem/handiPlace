package com.hackathon.handiplace.activities;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
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
import com.hackathon.handiplace.classes.Position;
import com.hackathon.handiplace.classes.Restaurant;
import com.hackathon.handiplace.classes.Utils;
import com.hackathon.handiplace.request.OkHttpStack;
import com.hackathon.handiplace.request.PostRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
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
    private String macAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mPosition = Utils.checkConnectionsReturnLocation(this);

        if(!HandiPlaceApplication.user.isConnected()) {
            login();
        }

    }

    @OnClick(R.id.restoFavorisButton)
    public void favRestos(View view) {

        String url = Utils.BASE_URL + "api/favorites/" + HandiPlaceApplication.user.getId() + "/longitudes/" + mPosition.getLongitude() + "/latitudes/" + mPosition.getLatitude() + "/get.json";

        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                ArrayList<Restaurant> restaurants = new ArrayList<>();

                try {
                    JSONArray jarray = new JSONArray(s);

                    for (int i = 0 ; i < jarray.length() ; i++) {

                        JSONObject jo = jarray.getJSONObject(i);
                        Restaurant res = new Restaurant(
                                jo.getString("name"),
                                jo.getString("category"),
                                jo.getInt("points"),
                                (jo.getDouble("distance") / 1000)
                        );

                        restaurants.add(res);
                    }

                    Intent intent = new Intent(MenuActivity.this, RestoListActivity.class);
                    intent.putExtra("restos", restaurants);
                    startActivity(intent);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(MenuActivity.this, "Error : " + volleyError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue queue = Volley.newRequestQueue(MenuActivity.this, new OkHttpStack());
        queue.add(request);

    }

    private void login() {
        WifiManager manager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = manager.getConnectionInfo();
        macAddress = info.getMacAddress();

        String URL = Utils.BASE_URL + "api/users/" + macAddress + ".json";
        StringRequest request = new StringRequest(URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {

                    JSONObject userJson = new JSONObject(s);

                    if (userJson.has("response")) {

                        if (userJson.getBoolean("response")) {

                            Toast.makeText(MenuActivity.this, "REPONSE", Toast.LENGTH_SHORT).show();

                            HandiPlaceApplication.user.setConnected(true);
                            HandiPlaceApplication.user.setId(userJson.getInt("id"));
                            JSONArray disArray = userJson.getJSONArray("disabilities");
                            for(int i = 0; i<disArray.length(); i++){
                                HandiPlaceApplication.user.setDisability(true, Utils.idHandicap.get(disArray.getInt(i)));
                            }
                        }
                        else {
                            // Renvoie une requête pour créer un compte
                            createUser();
                        }
                    } else {
                        // Erreur !
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                    new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(MenuActivity.this, volleyError.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue queue = Volley.newRequestQueue(MenuActivity.this, new OkHttpStack());
        queue.add(request);

    }

    private void createUser(){
        Map<String, String> params = new HashMap<String, String>();
        params.put("macAddress", macAddress);
        String URL = Utils.BASE_URL + "api/users.json";

        PostRequest requestAddUser = new PostRequest(URL, params, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try{
                    JSONObject userJSON = new JSONObject(s);
                    if (userJSON.has("response")) {

                        if (userJSON.getBoolean("response")) {
                            HandiPlaceApplication.user.setConnected(true);
                            HandiPlaceApplication.user.setId(userJSON.getInt("id"));
                            JSONArray disArray = userJSON.getJSONArray("disabilities");
                            for(int i = 0; i<disArray.length(); i++){
                                HandiPlaceApplication.user.setDisability(true, Utils.idHandicap.get(disArray.getInt(i)));
                            }
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

            String url = Utils.BASE_URL + "api/places/" + mPosition.getLongitude() + "/longitudes/" + mPosition.getLatitude() + "/latitude.json";

            StringRequest request = new StringRequest(url, new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {

                    ArrayList<Restaurant> restaurants = new ArrayList<>();

                    try {
                        JSONArray jarray = new JSONArray(s);

                        for (int i = 0 ; i < jarray.length() ; i++) {

                            JSONObject jo = jarray.getJSONObject(i);
                            Restaurant res = new Restaurant(
                                jo.getString("name"),
                                jo.getString("category"),
                                jo.getInt("points"),
                                (jo.getDouble("distance") / 1000)
                            );

                            restaurants.add(res);
                        }

                        Intent intent = new Intent(MenuActivity.this, RestoListActivity.class);
                        intent.putExtra("restos",restaurants);
                        startActivity(intent);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Toast.makeText(MenuActivity.this, volleyError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            RequestQueue queue = Volley.newRequestQueue(MenuActivity.this, new OkHttpStack());
            queue.add(request);



    }

    @OnClick (R.id.typeHandicapButton)
    public void startDisabledTypeActivity(View view){
        Utils.checkConnectionsReturnLocation(this);
        Intent intent = new Intent(this, DisabledTypeActivity.class);
        startActivity(intent);
    }

}
