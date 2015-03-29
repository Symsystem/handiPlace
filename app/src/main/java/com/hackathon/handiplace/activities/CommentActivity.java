package com.hackathon.handiplace.activities;


        import android.os.Bundle;
        import android.support.v7.app.ActionBarActivity;
        import android.app.AlertDialog;
        import android.content.Intent;
        import android.support.v7.app.ActionBarActivity;
        import android.os.Bundle;
        import android.support.v7.widget.Toolbar;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.Button;
        import android.widget.TextView;
        import android.widget.Toast;
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
        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;
        import java.util.HashMap;
        import java.util.Map;

public class CommentActivity extends ActionBarActivity {

    private TextView mComment;
    private Button mBackButton;
    private Button mSendButton;
    private Restaurant resto;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        resto = (Restaurant)intent.getSerializableExtra("resto");

        mComment = (TextView) findViewById(R.id.editText);
        mSendButton = (Button) findViewById(R.id.sendButton);
        mBackButton = (Button) findViewById(R.id.backButton);

        mBackButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                CommentActivity.this.finish();
            }
            });

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment = mComment.getText().toString().trim();
                if (comment.isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(CommentActivity.this);
                    builder.setTitle("Erreur");
                    builder.setMessage("Vous n'avez pas écrit de commentaire");
                    builder.setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("content", comment);
                    params.put("idUser", HandiPlaceApplication.user.getId() + "");
                    String URL = Utils.BASE_URL + "api/comments/" + resto.getId() + ".json";

                    PostRequest requestAddUser = new PostRequest(URL, params, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            try {
                                JSONObject userJSON = new JSONObject(s);
                                if (userJSON.has("response")) {
                                    if (userJSON.getBoolean("response")) {
                                        Toast.makeText(CommentActivity.this, "Commentaire publié ...", Toast.LENGTH_LONG).show();
                                        CommentActivity.this.finish();
                                    } else {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(CommentActivity.this);
                                        builder.setTitle("Erreur");
                                        builder.setMessage("Impossible de publier votre commentaire.");
                                        builder.setPositiveButton(android.R.string.ok, null);
                                        AlertDialog dialog = builder.create();
                                        dialog.show();
                                    }
                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(CommentActivity.this);
                                    builder.setTitle("Erreur");
                                    builder.setMessage("Impossible de publier votre commentaire.");
                                    builder.setPositiveButton(android.R.string.ok, null);
                                    AlertDialog dialog = builder.create();
                                    dialog.show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError volleyError) {

                                }
                            });
                    RequestQueue queue = Volley.newRequestQueue(CommentActivity.this, new OkHttpStack());
                    queue.add(requestAddUser);
                }
            }

    });
}
}