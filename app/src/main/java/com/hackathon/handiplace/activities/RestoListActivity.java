package com.hackathon.handiplace.activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.TextView;

import com.hackathon.handiplace.R;
import com.hackathon.handiplace.adapters.RestoAdapter;
import com.hackathon.handiplace.classes.Restaurant;

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

        RestoAdapter adapter = new RestoAdapter(this, restaurants);
        listView.setAdapter(adapter);
        listView.setEmptyView(empty);


    }

}
