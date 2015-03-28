package com.hackathon.handiplace.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.hackathon.handiplace.HandiPlaceApplication;
import com.hackathon.handiplace.R;
import com.hackathon.handiplace.classes.Utils;
import com.hackathon.handiplace.request.OkHttpStack;
import com.hackathon.handiplace.request.PostRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class DisabledTypeActivity extends ActionBarActivity {

    boolean[] selectedButtons;

    Toolbar mToolbar;

    ImageButton[] mButtons;

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
            lightMotorButton.setBackgroundResource(R.drawable.button_background);
        } else {
            selectedButtons[1] = true;
            lightMotorButton.setBackgroundResource(R.drawable.button_background_selected);
        }

    }

    @OnClick(R.id.disabled_type_3)
     public void selectBlindButton(View view) {

        if (selectedButtons[2]) {
            selectedButtons[2] = false;
            blindButton.setBackgroundResource(R.drawable.button_background);
        } else {
            selectedButtons[2] = true;
            blindButton.setBackgroundResource(R.drawable.button_background_selected);
        }

    }

    @OnClick(R.id.disabled_type_4)
    public void selectVewProblemsButton(View view) {

        if (selectedButtons[3]) {
            selectedButtons[3] = false;
            vewProblemsButton.setBackgroundResource(R.drawable.button_background);
        } else {
            selectedButtons[3] = true;
            vewProblemsButton.setBackgroundResource(R.drawable.button_background_selected);
        }

    }

    @OnClick(R.id.disabled_type_5)
    public void selectDeafButton(View view) {

        if (selectedButtons[4]) {
            selectedButtons[4] = false;
            deafButton.setBackgroundResource(R.drawable.button_background);
        } else {
            selectedButtons[4] = true;
            deafButton.setBackgroundResource(R.drawable.button_background_selected);
        }

    }

    @OnClick(R.id.disabled_type_6)
    public void selectHearingProblemesButton(View view) {

        if (selectedButtons[5]) {
            selectedButtons[5] = false;
            hearingProblemsButton.setBackgroundResource(R.drawable.button_background);
        } else {
            selectedButtons[5] = true;
            hearingProblemsButton.setBackgroundResource(R.drawable.button_background_selected);
        }

    }

    @OnClick(R.id.continue_button)
    public void sendResult() {
        Utils.checkConnectionsReturnLocation(this);

        if (HandiPlaceApplication.user == null) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Erreur");
            builder.setMessage("Une erreur est survenue...");
            builder.setPositiveButton(android.R.string.ok, null);
            AlertDialog dialog = builder.create();
            dialog.show();

            return;
        }

        String result = new String();

        if (selectedButtons[0]) {
            result += "1 ";
        }
        if (selectedButtons[1]) {
            result += "6 ";
        }
        if (selectedButtons[2]) {
            result += "2 ";
        }
        if (selectedButtons[3]) {
            result += "3 ";
        }
        if (selectedButtons[4]) {
            result += "4 ";
        }
        if (selectedButtons[5]) {
            result += "5 ";
        }

        Map params = new HashMap();
        params.put("idDisability", result);

        String url = Utils.BASE_URL + "api/users/" + HandiPlaceApplication.user.getId() + "/disabilities.json";

        PostRequest request = new PostRequest(url, params, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try{
                    JSONObject userJson = new JSONObject(s);

                    if (userJson.has("response")) {

                        if (userJson.getBoolean("response")) {

                            for(int i = 0; i< Utils.idHandicap.size(); i++){
                                if(selectedButtons[i])
                                    HandiPlaceApplication.user.setDisability(true, i);
                                else
                                    HandiPlaceApplication.user.setDisability(false, i);

                            }
                        }
                        else {
                            // Renvoie une requête pour créer un compte
                        }
                    } else {
                        // Erreur !
                    }
                }
                catch(JSONException e){
                    e.printStackTrace();
                }

                Intent intent = new Intent(DisabledTypeActivity.this, MenuActivity.class);
                startActivity(intent);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(DisabledTypeActivity.this, volleyError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue queue = Volley.newRequestQueue(DisabledTypeActivity.this, new OkHttpStack());
        queue.add(request);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disabled_type);
        ButterKnife.inject(this);

        mButtons = new ImageButton[Utils.idHandicap.size()];
        mButtons[0] = motorButton;
        mButtons[1] = lightMotorButton;
        mButtons[2] = blindButton;
        mButtons[3] = vewProblemsButton;
        mButtons[4] = deafButton;
        mButtons[5] = hearingProblemsButton;

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        selectedButtons = new boolean[Utils.idHandicap.size()];

        for (int i = 0; i< Utils.idHandicap.size(); i++){
           if(HandiPlaceApplication.user.getDisabilities()[i]){
               selectedButtons[i] = true;
               mButtons[i].setBackgroundResource(R.drawable.button_background_selected);
           }
        }

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
