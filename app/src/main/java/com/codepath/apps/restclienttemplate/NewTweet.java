package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

public class NewTweet extends AppCompatActivity {
    Button btn_new_tweet;
    EditText tv_compose;
    private TwitterClient client;
    ImageButton btn_cancel;
    String username;
    ImageView account_profil;
    TextView account_user,account_screen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_tweet);
        //for custom actionbar
        Toolbar toolbar=findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        LayoutInflater layoutInflater=(LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View action_bar_view=layoutInflater.inflate(R.layout.tweet_custom_bar,null);
        actionBar.setCustomView(action_bar_view);
        //for custom actionbar
        btn_cancel=findViewById(R.id.btn_cancel);
        btn_new_tweet=findViewById(R.id.btn_new_tweet);
        tv_compose=findViewById(R.id.tv_compose);
        client= TwitterApplication.getRestClient(this);
        GetAccountsetting();
        btn_new_tweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String compose=tv_compose.getText().toString();
                if(compose.isEmpty()){
                    Toast.makeText(NewTweet.this, "Your tweet is empty!!!", Toast.LENGTH_LONG).show();
                    return;
                }

                if(compose.length()>140){
                    Toast.makeText(NewTweet.this, "Your tweet is too long!!!", Toast.LENGTH_LONG).show();
                    return;
                }
                client.ComposeTweet(compose,new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        Log.d("NewTwitter","Successfully "+ response.toString());
                        try {
                            Tweet tweet= Tweet.fromJson(response);
                            Intent data=new Intent();
                            data.putExtra("tweet", Parcels.wrap(tweet));
                            setResult(RESULT_OK,data);
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);
                    }
                });

            }
        });


        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void GetAccountsetting() {
        client.acountSetting(new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    username=response.getString("screen_name");
                    client.accountinfo(username,new JsonHttpResponseHandler(){
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            Log.d("TwitterClientProfile",username );
                            String profile_img= null;
                            try {

                                account_user=findViewById(R.id.account_user);
                                account_profil=findViewById(R.id.new_twet);
                                account_screen=findViewById(R.id.account_screen);
                                account_user.setText(response.getString("name"));
                                account_screen.setText("@" +response.getString("screen_name"));
                                profile_img = response.getString("profile_image_url");
                                Glide.with(NewTweet.this).load(profile_img).into(account_profil);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                            super.onFailure(statusCode, headers, throwable, errorResponse);

                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            super.onFailure(statusCode, headers, responseString, throwable);
                            Log.d("TwitterClientProfile",responseString);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }
}
