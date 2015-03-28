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
            motorButton.setBackgroundResource(R.drawable.button_background);
        } else {
            selectedButtons[0] = true;
            motorButton.setBackgroundResource(R.drawable.button_background_selected);

        }

    }

    @OnClick(R.id.disabled_type_2)
    public void selectLightMotorButton(View view) {

        if (selectedButtons[1]) {
            selectedButtons[1] = false;
            lightMotorButton.setBackgroundResource(R.drawable.button_background);;
        } else {
            selectedButtons[1] = true;
            lightMotorButton.setBackgroundResource(R.drawable.button_background_selected);
        }

    }

    @OnClick(R.id.disabled_type_3)
     public void selectBlindButton(View view) {

        if (selectedButtons[2]) {
            selectedButtons[2] = false;
            blindButton.setBackgroundResource(R.drawable.button_background);;
        } else {
            selectedButtons[2] = true;
            blindButton.setBackgroundResource(R.drawable.button_background_selected);
        }

    }

    @OnClick(R.id.disabled_type_4)
    public void selectVewProblemsButton(View view) {

        if (selectedButtons[3]) {
            selectedButtons[3] = false;
            vewProblemsButton.setBackgroundResource(R.drawable.button_background);;
        } else {
            selectedButtons[3] = true;
            vewProblemsButton.setBackgroundResource(R.drawable.button_background_selected);
        }

    }

    @OnClick(R.id.disabled_type_5)
    public void selectDeafButton(View view) {

        if (selectedButtons[4]) {
            selectedButtons[4] = false;
            deafButton.setBackgroundResource(R.drawable.button_background);;
        } else {
            selectedButtons[4] = true;
            deafButton.setBackgroundResource(R.drawable.button_background_selected);
        }

    }

    @OnClick(R.id.disabled_type_6)
    public void selectHearingProblemesButton(View view) {

        if (selectedButtons[5]) {
            selectedButtons[5] = false;
            hearingProblemsButton.setBackgroundResource(R.drawable.button_background);;
        } else {
            selectedButtons[5] = true;
            hearingProblemsButton.setBackgroundResource(R.drawable.button_background_selected);
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
}
