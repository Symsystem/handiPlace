package com.hackathon.handiplace.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hackathon.handiplace.HandiPlaceApplication;
import com.hackathon.handiplace.R;
import com.hackathon.handiplace.adapters.RestoAdapter;
import com.hackathon.handiplace.classes.Criterion;
import com.hackathon.handiplace.classes.Disability;
import com.hackathon.handiplace.classes.Restaurant;
import com.hackathon.handiplace.classes.Utils;
import com.hackathon.handiplace.request.OkHttpStack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class RestoListActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {


    private List<Restaurant> restaurants;

    Toolbar toolbar;
    @InjectView(R.id.restoList) ListView listView;
    @InjectView(android.R.id.empty) TextView empty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resto_list);

        ButterKnife.inject(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent gottenIntent = getIntent();
        restaurants = (List<Restaurant>) gottenIntent.getSerializableExtra("restos");

        RestoAdapter adapter = new RestoAdapter(this, restaurants);
        listView.setAdapter(adapter);
        listView.setEmptyView(empty);


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        final Restaurant resto = restaurants.get(position);

        String url = Utils .BASE_URL + "api/places/" + resto.getId() + "/users/" + HandiPlaceApplication.user.getId() + ".json";
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject jsonDetailsResto = new JSONObject(s);

                    resto.setId(jsonDetailsResto.getInt("id"));
                    resto.setAddress(jsonDetailsResto.getString("address"));
                    resto.setCategory(jsonDetailsResto.getString("categorie"));

                    JSONArray criterionArray = jsonDetailsResto.getJSONArray("criterion");

                    String nameDisability = new String();
                    JSONArray disTempArray;

                    Disability[] isAlreadyDis = new Disability[6];
                    JSONObject critObj, disObj;

                    for(int i = 0; i < criterionArray.length(); i++){
                        critObj = criterionArray.getJSONObject(i);
                        Criterion crit = new Criterion(critObj.getString("name"),
                                critObj.getString("description"),
                                critObj.getBoolean("goodOrBad"),
                                critObj.getInt("userLike"),
                                critObj.getInt("nbLike"),
                                critObj.getInt("nbDislike"));

                        disTempArray = critObj.getJSONArray("disability");

                        for(int j = 0; j<disTempArray.length(); j++){
                            disObj = disTempArray.getJSONObject(j);

                            int id = disObj.getInt("id");
                            if(!(isAlreadyDis[id].getId() == id)){

                                Disability dis = new Disability(id,
                                        disObj.getString("name"),
                                        disObj.getString("description"),
                                        new ArrayList<Criterion>());
                                isAlreadyDis[id] = dis;
                            }

                            isAlreadyDis[id].getCriterions().add(crit);
                        }
                    }

                    ArrayList<Disability> disArray = new ArrayList<>();
                    for(int i = 0; i<isAlreadyDis.length; i++){
                        disArray.add(isAlreadyDis[0]);
                    }
                    resto.setDisabilities(disArray);

                    Intent intent = new Intent(RestoListActivity.this, RestoDetailsActivity.class);
                    intent.putExtra("resto", resto);
                    startActivity(intent);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener(){

        @Override
        public void onErrorResponse(VolleyError volleyError) {
            Toast.makeText(RestoListActivity.this, volleyError.getMessage(), Toast.LENGTH_SHORT).show();
        }
    });

        RequestQueue queue = Volley.newRequestQueue(RestoListActivity.this, new OkHttpStack());
        queue.add(request);

        Intent intent = new Intent(RestoListActivity.this, RestoDetailsActivity.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
