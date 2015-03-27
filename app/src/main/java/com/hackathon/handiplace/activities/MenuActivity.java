package com.hackathon.handiplace.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.hackathon.handiplace.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class MenuActivity extends ActionBarActivity {

    Toolbar mToolbar;

    @InjectView(R.id.restoLocationButton) Button mRestoLocation;
    @InjectView(R.id.restoListButton) Button mRestoList;
    @InjectView(R.id.restoFavorisButton) Button mRestoFavoris;
    @InjectView(R.id.typeHandicapButton) Button mTypeHandicap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


    }

    @OnClick (R.id.typeHandicapButton)
    public void startDisabledTypeActivity(View view){
        Intent intent = new Intent(this, DisabledTypeActivity.class);
        startActivity(intent);
    }

}
