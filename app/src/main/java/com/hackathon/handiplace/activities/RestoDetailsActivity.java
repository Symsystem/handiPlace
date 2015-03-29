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

import com.hackathon.handiplace.HandiPlaceApplication;
import com.hackathon.handiplace.R;
import com.hackathon.handiplace.classes.Restaurant;
import com.hackathon.handiplace.classes.Utils;
import com.squareup.picasso.Picasso;

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

    private ImageButton[] disabledType = {motor, lightMotor, blind, viewProblems, deaf, hearingProblems};
    boolean[] selected = new boolean[HandiPlaceApplication.user.getDisabilities().length];
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resto_details);

        ButterKnife.inject(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
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
            }
        });

        // image
        String url = Utils.BASE_URL + resto.getImageURL();
        Picasso.with(this).load(url).into(headerImage);

        // name
        restoName.setText(resto.getName());

        // desc
        restoDesc.setText(resto.getDescription());




        for(int i = 0; i<selected.length; i++){
            if(HandiPlaceApplication.user.getDisabilities()[i]) {
                selected[i] = true;
                break;
            }
            if (i == HandiPlaceApplication.user.getDisabilities().length - 1 && !HandiPlaceApplication.user.getDisabilities()[i])
                selected[0] = true;
        }

    }

    @OnClick (R.id.motor)
    public void onClickMotor(){
        for(int i = 0; i<selected.length; i++){

        }
    }

    @OnClick (R.id.light_motor)
    public void onClickLightMotor(){

    }
    @OnClick (R.id.blind)
    public void onClickBlind(){

    }
    @OnClick (R.id.view_problems)
    public void onClickViewProblems(){

    }
    @OnClick (R.id.deaf)
    public void onClickDeaf(){

    }
    @OnClick (R.id.hearing_problems)
    public void onClickHearingProblems(){

    }

    @OnClick (R.id.new_comment)
    public void onClickNewComment(){
        Intent intent = new Intent(RestoDetailsActivity.this, CommentActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("resto_id", resto.getId());
        startActivity(intent);
    }


}
