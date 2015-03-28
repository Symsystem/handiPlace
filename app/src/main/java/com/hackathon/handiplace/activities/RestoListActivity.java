package com.hackathon.handiplace.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.TextView;

import com.hackathon.handiplace.R;
import com.hackathon.handiplace.adapters.RestoAdapter;
import com.hackathon.handiplace.classes.Restaurant;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class RestoListActivity extends ActionBarActivity {


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

        restaurants = new ArrayList<Restaurant>();

        restaurants.add(new Restaurant("Au délice", "Venez vous régaler chez nous!", "Frites",
                "Impasse de la Fidélité 4A 1000 Ville de Bruxelles", 2, null));
        restaurants.add(new Restaurant("Pépé café", "Pour bien manger !", "Italien",
                "Rue de Tabora 11 1000 Ville de Bruxelles", 3, null));
        restaurants.add(new Restaurant("Xu ji", "Venez vous régaler chez nous!", "Chinois",
                "Rue des Poissonniers 5 1000 Ville de Bruxelles", 1, null));

        RestoAdapter adapter = new RestoAdapter(this, restaurants);
        listView.setAdapter(adapter);
        listView.setEmptyView(empty);
    }

}
