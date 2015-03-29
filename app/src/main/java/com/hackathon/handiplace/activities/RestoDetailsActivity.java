package com.hackathon.handiplace.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.IconTextView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.hackathon.handiplace.HandiPlaceApplication;
import com.hackathon.handiplace.R;
import com.hackathon.handiplace.classes.Restaurant;
import com.hackathon.handiplace.classes.Utils;
import com.hackathon.handiplace.request.OkHttpStack;
import com.hackathon.handiplace.request.PostRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

//if (view.getBackground() == getResources().getDrawable(R.drawable.button_background)) {
//    view.setBackgroundResource(R.drawable.button_background_selected);
//} else {
//    view.setBackgroundResource(R.drawable.button_background);
//}

public class RestoDetailsActivity extends ActionBarActivity {

    private boolean isFav;
    private Restaurant resto;

    @InjectView(R.id.header_image) ImageView headerImage;
    @InjectView(R.id.fav) ImageButton fav;
    @InjectView(R.id.resto_name) TextView restoName;
    @InjectView(R.id.resto_desc) TextView restoDesc;
    @InjectView(R.id.motor) ImageButton motor;
    @InjectView(R.id.motor_stars) IconTextView motorStars;
    @InjectView(R.id.light_motor) ImageButton lightMotor;
    @InjectView(R.id.light_motor_stars) IconTextView lightMotorStars;
    @InjectView(R.id.blind) ImageButton blind;
    @InjectView(R.id.blind_stars) IconTextView blindStars;
    @InjectView(R.id.view_problems) ImageButton viewProblems;
    @InjectView(R.id.view_problems_stars) IconTextView viewProblemsStars;
    @InjectView(R.id.deaf) ImageButton deaf;
    @InjectView(R.id.deaf_stars) IconTextView deafStars;
    @InjectView(R.id.hearing_problems) ImageButton hearingProblems;
    @InjectView(R.id.hearing_problems_stars) IconTextView hearingProblemsStars;
    @InjectView(R.id.new_comment) Button newCommentButton;

    private ImageButton[] disabledType;
    int selected;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resto_details);

        ButterKnife.inject(this);

        disabledType = new ImageButton[6];
        disabledType[0] = motor;
        disabledType[1] = lightMotor;
        disabledType[2] = blind;
        disabledType[3] = viewProblems;
        disabledType[4] = deaf;
        disabledType[5] = hearingProblems;

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Intent intent = getIntent();
        resto = (Restaurant)intent.getSerializableExtra("resto");

        // FAV
        isFav = resto.isFavorite();
        if (!isFav) {
            fav.setImageResource(R.drawable.favorisavant);
            fav.setContentDescription("Ajouter le restaurant dans mes favoris");
            isFav = false;
        } else {
            fav.setImageResource(R.drawable.favorisapres);
            fav.setContentDescription("Retirer le restaurant dans mes favoris");
            isFav = true;
        }

        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFav) {
                    fav.setImageResource(R.drawable.favorisavant);
                    fav.setContentDescription("Ajouter le restaurant dans mes favoris");
                    isFav = false;
                } else {
                    fav.setImageResource(R.drawable.favorisapres);
                    fav.setContentDescription("Retirer le restaurant dans mes favoris");
                    isFav = true;
                }
                String url = Utils.BASE_URL + "api/favorites/" + HandiPlaceApplication.user.getId() + ".json";
                Map<String, String> params = new HashMap<>();
                params.put("idPlace", resto.getId()+"");

                PostRequest request = new PostRequest(url, params, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try{
                            JSONObject jsonObject = new JSONObject(s);
                            if(jsonObject.getBoolean("response")){

                            }
                            else{
                                if (isFav) {
                                    fav.setImageResource(R.drawable.favorisavant);
                                    isFav = false;
                                } else {
                                    fav.setImageResource(R.drawable.favorisapres);
                                    isFav = true;
                                }
                            }
                        }
                        catch(JSONException e){

                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                });

                RequestQueue queue = Volley.newRequestQueue(RestoDetailsActivity.this, new OkHttpStack());
                queue.add(request);
            }
       });



        // image
        String url = Utils.BASE_URL + resto.getImageURL();
        Picasso.with(this).load(url).into(headerImage);

        // name
        restoName.setText(resto.getName());

        // desc
        restoDesc.setText(resto.getDescription());




        for(int i = 0; i<Utils.idHandicap.size(); i++){
            if(HandiPlaceApplication.user.getDisabilities()[i]) {
                selected = i;
                disabledType[i].setBackgroundResource(R.drawable.button_background_selected);
                break;
            }
            if (i == Utils.idHandicap.size()-1 && !(HandiPlaceApplication.user.getDisabilities()[i])){
                selected = 0;
                disabledType[0].setBackgroundResource(R.drawable.button_background_selected);
            }
        }

    }

    @OnClick (R.id.motor)
    public void onClickMotor(View view){
        disabledType[selected].setBackgroundResource(R.drawable.button_background);
        selected = 0;
        disabledType[0].setBackgroundResource(R.drawable.button_background_selected);
    }

    @OnClick (R.id.light_motor)
    public void onClickLightMotor(View view){
        disabledType[selected].setBackgroundResource(R.drawable.button_background);
        selected = 1;
        disabledType[1].setBackgroundResource(R.drawable.button_background_selected);
    }
    @OnClick (R.id.blind)
    public void onClickBlind(View view){
        disabledType[selected].setBackgroundResource(R.drawable.button_background);
        selected = 2;
        disabledType[2].setBackgroundResource(R.drawable.button_background_selected);
    }
    @OnClick (R.id.view_problems)
    public void onClickViewProblems(View view){
        disabledType[selected].setBackgroundResource(R.drawable.button_background);
        selected = 3;
        disabledType[3].setBackgroundResource(R.drawable.button_background_selected);
    }
    @OnClick (R.id.deaf)
    public void onClickDeaf(View view){
        disabledType[selected].setBackgroundResource(R.drawable.button_background);
        selected = 4;
        disabledType[4].setBackgroundResource(R.drawable.button_background_selected);
    }
    @OnClick (R.id.hearing_problems)
    public void onClickHearingProblems(View view){
        disabledType[selected].setBackgroundResource(R.drawable.button_background);
        selected = 5;
        disabledType[5].setBackgroundResource(R.drawable.button_background_selected);
    }

    @OnClick (R.id.new_comment)
    public void onClickNewComment(){
        Intent intent = new Intent(RestoDetailsActivity.this, CommentActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("resto", resto);
        startActivity(intent);
    }


}
