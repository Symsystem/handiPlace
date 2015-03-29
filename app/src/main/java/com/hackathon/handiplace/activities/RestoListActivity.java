package com.hackathon.handiplace.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
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

    ProgressBar spinner;

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

        spinner = (ProgressBar) findViewById(R.id.spinner);

        Intent gottenIntent = getIntent();
        restaurants = (List<Restaurant>) gottenIntent.getSerializableExtra("restos");

        RestoAdapter adapter = new RestoAdapter(this, restaurants);
        listView.setAdapter(adapter);
        listView.setEmptyView(empty);
        listView.setOnItemClickListener(this);


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        spinner.setVisibility(View.VISIBLE);

        final Restaurant resto = restaurants.get(position);

        String url = Utils .BASE_URL + "api/places/" + resto.getId() + "/users/" + HandiPlaceApplication.user.getId() + ".json";
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject jsonDetailsResto = new JSONObject(s);

                    resto.setId(jsonDetailsResto.getInt("id"));
                    resto.setAddress(jsonDetailsResto.getString("address"));
                    resto.setCategory(jsonDetailsResto.getString("category"));
                    resto.setFavorite(jsonDetailsResto.getBoolean("isFavorite"));
                    resto.setImageURL(jsonDetailsResto.getString("imgUrl"));
                    resto.setDescription(jsonDetailsResto.getString("description"));

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
                                critObj.getInt("nbDislike"),
                                critObj.getInt("priority"));

                        disTempArray = critObj.getJSONArray("disability");

                        for(int j = 0; j<disTempArray.length(); j++){
                            disObj = disTempArray.getJSONObject(j);

                            int id = disObj.getInt("id");
                            if(isAlreadyDis[id-1] == null){

                                isAlreadyDis[id-1] = new Disability(id,
                                        disObj.getString("name"),
                                        disObj.getString("description"),
                                        new ArrayList<Criterion>());
                            }

                            isAlreadyDis[id-1].getCriterions().add(crit);
                        }
                    }

                    ArrayList<Disability> disArray = new ArrayList<>();
                    for(int i = 0; i<isAlreadyDis.length; i++){
                        disArray.add(isAlreadyDis[i]);
                    }
                    resto.setDisabilities(disArray);

                    spinner.setVisibility(View.GONE);


                    Intent intent = new Intent(RestoListActivity.this, RestoDetailsActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("resto", resto);
                    startActivity(intent);

                } catch (JSONException e) {
                    spinner.setVisibility(View.GONE);
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener(){

        @Override
        public void onErrorResponse(VolleyError volleyError) {
            spinner.setVisibility(View.GONE);
            Toast.makeText(RestoListActivity.this, volleyError.getMessage(), Toast.LENGTH_SHORT).show();
        }
    });

        RequestQueue queue = Volley.newRequestQueue(RestoListActivity.this, new OkHttpStack());
        queue.add(request);

    }
}
