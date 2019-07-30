package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class detail extends AppCompatActivity {
    public ImageView imgProfile,img_post;
    public TextView TVScreenName,TVbody,tv_time,tv_comment,tv_like,tv_retweet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        imgProfile=findViewById(R.id.img_profil);
        TVScreenName=findViewById(R.id.tv_name);
        TVbody=findViewById(R.id.tv_tweet);
        tv_time=findViewById(R.id.tv_time);
        tv_like=findViewById(R.id.tv_like);
        tv_comment=findViewById(R.id.tv_comment);
        tv_retweet=findViewById(R.id.tv_retweet);
        img_post=findViewById(R.id.img_psot);

        Intent i=getIntent();
        Glide.with(this)
                .load(i.getStringExtra("image"))
                .into(imgProfile);

        Glide.with(this)
                .load(i.getStringExtra("imagepost"))
                .into(img_post);
        TVbody.setText(i.getStringExtra("body"));
        TVScreenName.setText(i.getStringExtra("screen"));
        String time=TimeFormatter.getTimeDifference(i.getStringExtra("time"));
        tv_time.setText(time);
        tv_like.setText(i.getStringExtra("like"));
        tv_retweet.setText(i.getStringExtra("retweet"));

    }
}
