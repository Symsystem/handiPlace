package com.hackathon.handiplace.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.hackathon.handiplace.R;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class InternetActivity extends ActionBarActivity {

    Toolbar toolbar;

    @InjectView(R.id.wifiButton) Button mWifiButton;
    @InjectView(R.id.cellButton) Button mCellButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internet);

        ButterKnife.inject(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    @OnClick (R.id.wifiButton)
    public void clickWifiButton(){
        WifiManager wM = (WifiManager) getSystemService(WIFI_SERVICE);
        wM.setWifiEnabled(true);
        Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
        startActivity(intent);

        //finish();
    }

    @OnClick (R.id.cellButton)
    public void clickCellButton(){

        Intent intent=new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS);
        ComponentName cn = new ComponentName("com.android.phone","com.android.phone.Settings");
        intent.setComponent(cn);
        startActivity(intent);
    }


}
