package com.hackathon.handiplace.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.IconTextView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hackathon.handiplace.HandiPlaceApplication;
import com.hackathon.handiplace.R;
import com.hackathon.handiplace.adapters.CriteresAdapter;
import com.hackathon.handiplace.classes.Criterion;
import com.hackathon.handiplace.classes.Disability;
import com.hackathon.handiplace.classes.Restaurant;
import com.hackathon.handiplace.classes.Utils;
import com.hackathon.handiplace.request.OkHttpStack;
import com.hackathon.handiplace.request.PostRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
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
    private boolean openDesc;

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

    //@InjectView(R.id.list_critere) ListView listCritere;
    @InjectView(R.id.plusContent) TextView plusContent;
    @InjectView(R.id.minusContent) TextView minusContent;
    @InjectView(R.id.comment) TextView commentTextView;
    CriteresAdapter adapter;

    private ImageButton[] disabledType;
    private IconTextView[] stars;
    int selected;
    Toolbar toolbar;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                RestoDetailsActivity.this.finish();
        }
        return true;
    }

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

        // Adapter pour la liste des critères :
        /*listCritere = (ListView) findViewById(R.id.list_critere);
        adapter = new CriteresAdapter(this, resto.getDisabilities().get(selected).getCriterions());
        //listCritere.getLayoutParams().height = 115 * adapter.getCount();
        listCritere.setAdapter(adapter);*/

        //listCritere.setScrollContainer(false);

        //Dessine et calcule les étoiles
        stars = new IconTextView[6];
        stars[0] = motorStars;
        stars[1] = lightMotorStars;
        stars[2] = blindStars;
        stars[3] = viewProblemsStars;
        stars[4] = deafStars;
        stars[5] = hearingProblemsStars;

        writeStars();

        //Met à jour les critères :
        writeCriterion(selected);

        // Mets à jour les commentaires
        updateComment();

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

        restoName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (openDesc) {

                    restoDesc.setVisibility(View.GONE);
                    openDesc = false;

                } else {

                    restoDesc.setVisibility(View.VISIBLE);
                    openDesc = true;

                }

            }
        });


    }

    private void updateComment(){
        String urlComments = Utils .BASE_URL + "api/comments/" + resto.getId() + ".json";
        StringRequest request = new StringRequest(urlComments, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONArray jsonComments = new JSONArray(s);
                    String comment = "";
                    for(int i=0; i < jsonComments.length(); i++) {
                        JSONObject jsonComment = jsonComments.getJSONObject(i);
                        comment += jsonComment.getString("content") + "\n\n";
                    }
                    commentTextView.setText(comment);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(RestoDetailsActivity.this, volleyError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue queue = Volley.newRequestQueue(RestoDetailsActivity.this, new OkHttpStack());
        queue.add(request);
    }

    @OnClick (R.id.fav)
    public void onClickFav(View view){
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
                }
                catch(JSONException e){

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
    }

    private void writeCriterion(int disabledSelected){
        String strOK = "";
        String strMauvais = "";
        for(int i = 0; i < resto.getDisabilities().get(disabledSelected).getCriterions().size(); i++){
            if(resto.getDisabilities().get(disabledSelected).getCriterions().get(i).isGooOrBad()){
                strOK += resto.getDisabilities().get(disabledSelected).getCriterions().get(i).getName() + "\n\n";
            }
            else {
                strMauvais += resto.getDisabilities().get(disabledSelected).getCriterions().get(i).getName() + "\n\n";
            }
        }
        minusContent.setText(strMauvais);
        plusContent.setText(strOK);
    }

    private void writeStars(){
        int tot = 0, somme = 0;
        ArrayList<Disability> disabilities = resto.getDisabilities();
        ArrayList<Criterion> criterions;
        for(int i = 0; i < Utils.idHandicap.size(); i++){
            criterions = disabilities.get(i).getCriterions();
            for(int j = 0; j < criterions.size(); j++){
                Criterion c = criterions.get(j);
                if(c.isGooOrBad())
                    somme += c.getPriority();
                tot += c.getPriority();
            }
            //int rate = new Random().nextInt(5)+1;
            double d = ((double)somme / (double)tot) * 5;
            int rate = (int)Math.round(d);
            String s = new String();

            for(int j = 0; j<rate; j++){
                s += "{fa-star}";
            }

            stars[i].setText(s);
        }


    }

    @OnClick (R.id.motor)
    public void onClickMotor(View view){
        disabledType[selected].setBackgroundResource(R.drawable.button_background);
        selected = 0;
        disabledType[0].setBackgroundResource(R.drawable.button_background_selected);
        //adapter.changeCriterions(resto.getDisabilities().get(selected).getCriterions());
        //adapter.notifyDataSetChanged();
        writeCriterion(selected);
    }

    @OnClick (R.id.light_motor)
    public void onClickLightMotor(View view){
        disabledType[selected].setBackgroundResource(R.drawable.button_background);
        selected = 1;
        disabledType[1].setBackgroundResource(R.drawable.button_background_selected);
        //adapter.changeCriterions(resto.getDisabilities().get(selected).getCriterions());
        //adapter.notifyDataSetChanged();
        writeCriterion(selected);
    }
    @OnClick (R.id.blind)
    public void onClickBlind(View view){
        disabledType[selected].setBackgroundResource(R.drawable.button_background);
        selected = 2;
        disabledType[2].setBackgroundResource(R.drawable.button_background_selected);
        //adapter.changeCriterions(resto.getDisabilities().get(selected).getCriterions());
        //adapter.notifyDataSetChanged();
        writeCriterion(selected);
    }
    @OnClick (R.id.view_problems)
    public void onClickViewProblems(View view){
        disabledType[selected].setBackgroundResource(R.drawable.button_background);
        selected = 3;
        disabledType[3].setBackgroundResource(R.drawable.button_background_selected);
        //adapter.changeCriterions(resto.getDisabilities().get(selected).getCriterions());
        //adapter.notifyDataSetChanged();
        writeCriterion(selected);
    }
    @OnClick (R.id.deaf)
    public void onClickDeaf(View view){
        disabledType[selected].setBackgroundResource(R.drawable.button_background);
        selected = 4;
        disabledType[4].setBackgroundResource(R.drawable.button_background_selected);
        //adapter.changeCriterions(resto.getDisabilities().get(selected).getCriterions());
        //adapter.notifyDataSetChanged();
        writeCriterion(selected);
    }
    @OnClick (R.id.hearing_problems)
    public void onClickHearingProblems(View view){
        disabledType[selected].setBackgroundResource(R.drawable.button_background);
        selected = 5;
        disabledType[5].setBackgroundResource(R.drawable.button_background_selected);
        //adapter.changeCriterions(resto.getDisabilities().get(selected).getCriterions());
        //adapter.notifyDataSetChanged();
        writeCriterion(selected);
    }

    @OnClick (R.id.new_comment)
    public void onClickNewComment(){
        Intent intent = new Intent(RestoDetailsActivity.this, CommentActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("resto_id", resto.getId());
        startActivity(intent);
    }


}
