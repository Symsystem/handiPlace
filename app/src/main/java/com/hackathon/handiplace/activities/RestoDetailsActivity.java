package com.hackathon.handiplace.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.hackathon.handiplace.R;
import com.hackathon.handiplace.classes.Restaurant;

//if (view.getBackground() == getResources().getDrawable(R.drawable.button_background)) {
//    view.setBackgroundResource(R.drawable.button_background_selected);
//} else {
//    view.setBackgroundResource(R.drawable.button_background);
//}

public class RestoDetailsActivity extends ActionBarActivity {

    private boolean isFav;
    private ImageView favView;
    private Restaurant resto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resto_details);

        favView = (ImageButton) findViewById(R.id.fav);

        favView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFav) {
                    favView.setImageResource(R.drawable.favorisavant);
                    favView.setContentDescription("Ajouter le restaurant dans mes favoris");
                    isFav = false;
                } else {
                    favView.setImageResource(R.drawable.favorisapres);
                    favView.setContentDescription("Retirer le restaurant dans mes favoris");
                    isFav = true;
                }
            }
        });

        Intent intent = getIntent();
        resto = (Restaurant)intent.getSerializableExtra("resto");


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_resto_details, menu);
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
