package com.hackathon.handiplace.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.hackathon.handiplace.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class DisabledTypeActivity extends ActionBarActivity {

    boolean[] selectedButtons;

    Toolbar mToolbar;

    @InjectView(R.id.disabled_type_1)
    ImageButton motorButton;

    @InjectView(R.id.disabled_type_2)
    ImageButton lightMotorButton;

    @InjectView(R.id.disabled_type_3)
    ImageButton blindButton;

    @InjectView(R.id.disabled_type_4)
    ImageButton vewProblemsButton;

    @InjectView(R.id.disabled_type_5)
    ImageButton deafButton;

    @InjectView(R.id.disabled_type_6)
    ImageButton hearingProblemsButton;

    @InjectView(R.id.continue_button)
    Button continueButton;

    @OnClick(R.id.disabled_type_1)
    public void selectMotorButton(View view) {

        if (selectedButtons[0]) {
            selectedButtons[0] = false;
            motorButton.setImageResource(R.drawable.disabled_icon);
        } else {
            selectedButtons[0] = true;
            motorButton.setImageResource((R.drawable.disabled_icon_selected));
        }

    }

    @OnClick(R.id.disabled_type_2)
    public void selectLightMotorButton(View view) {

        if (selectedButtons[1]) {
            selectedButtons[1] = false;
            motorButton.setImageResource(R.drawable.nottotallyblind_icon);
        } else {
            selectedButtons[1] = true;
            motorButton.setImageResource((R.drawable.nottotallydisabled_selected));
        }

    }

    @OnClick(R.id.disabled_type_3)
     public void selectBlindButton(View view) {

        if (selectedButtons[2]) {
            selectedButtons[2] = false;
            motorButton.setImageResource(R.drawable.blind_icon);
        } else {
            selectedButtons[2] = true;
            motorButton.setImageResource((R.drawable.blind_icon_selected));
        }

    }

    @OnClick(R.id.disabled_type_4)
    public void selectVewProblemsButton(View view) {

        if (selectedButtons[3]) {
            selectedButtons[3] = false;
            motorButton.setImageResource(R.drawable.nottotallyblind_icon);
        } else {
            selectedButtons[3] = true;
            motorButton.setImageResource((R.drawable.nottotallyblind_icon_selected));
        }

    }

    @OnClick(R.id.disabled_type_5)
    public void selectDeafButton(View view) {

        if (selectedButtons[4]) {
            selectedButtons[4] = false;
            motorButton.setImageResource(R.drawable.deaf_icon);
        } else {
            selectedButtons[4] = true;
            motorButton.setImageResource((R.drawable.deaf_icon_selected));
        }

    }

    @OnClick(R.id.disabled_type_6)
    public void selectHearingProblemesButton(View view) {

        if (selectedButtons[5]) {
            selectedButtons[5] = false;
            motorButton.setImageResource(R.drawable.nottotallydeaf_icon);
        } else {
            selectedButtons[5] = true;
            motorButton.setImageResource((R.drawable.nottotallydeaf_icon_selected));
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disabled_type);
        ButterKnife.inject(this);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        selectedButtons = new boolean[6];
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_disabled_type, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
