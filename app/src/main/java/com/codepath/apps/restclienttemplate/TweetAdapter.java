package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import java.util.ArrayList;
import java.util.List;

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHOlder> {
 private Context context;
 private List<Tweet> tweets;

    //pass in contex in list of tweets

    public TweetAdapter(Context context, List<Tweet> tweets) {
        this.context = context;
        this.tweets = tweets;
    }

    //for each row inflate the layout
    @NonNull
    @Override
    public ViewHOlder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.tweet,viewGroup,false);
        return new ViewHOlder(view);
    }
    //bind value base on position

    @Override
    public void onBindViewHolder(@NonNull ViewHOlder viewHOlder, final int i) {
        viewHOlder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent im=new Intent(context,detail.class);
                im.putExtra("image",tweets.get(i).user.ImageUrl);
                im.putExtra("body",tweets.get(i).body);
                im.putExtra("screen",tweets.get(i).user.ScreenName);
                im.putExtra("time",tweets.get(i).createdat);
                im.putExtra("retweet",tweets.get(i).retweet);
                im.putExtra("like",tweets.get(i).favorite);
                im.putExtra("imagepost",tweets.get(i).image);
                context.startActivity(im);
            }
        });
        Tweet tweet=tweets.get(i);
        viewHOlder.TVbody.setText(tweet.body);
        viewHOlder.TVScreenName.setText(tweet.user.ScreenName);
        String time = TimeFormatter.getTimeDifference(tweet.createdat);
        viewHOlder.tv_time.setText(time);
        Glide.with(context)
                .load(tweet.user.ImageUrl)
                .into(viewHOlder.imgProfile);
     viewHOlder.tv_retweet.setText(tweet.retweet);
      viewHOlder.tv_like.setText(tweet.favorite);
        if(tweet.type.contentEquals("photo")){
            Glide.with(context)
                    .load(tweet.image)
                    .into(viewHOlder.img_post);
        }else if(tweet.type.contentEquals(""))
        {
            viewHOlder.img_post.setVisibility(View.INVISIBLE);
        }

    }


    @Override
    public int getItemCount() {
        return tweets.size();
    }
//clean the recylcerview
    public void clear(){
        tweets.clear();
        notifyDataSetChanged();
    }
//add all element in the list
    public void addTweet(List<Tweet> tweetList){
      //  tweets=new ArrayList<>();
        tweets.addAll(tweetList);
        notifyDataSetChanged();
    }

    //Define the viewholder
    public class ViewHOlder extends RecyclerView.ViewHolder{
        public ConstraintLayout constraintLayout;
        public ImageView imgProfile,img_post;
        public TextView TVScreenName,TVbody,tv_time,tv_comment,tv_like,tv_retweet;


        public ViewHOlder(@NonNull View itemView) {
            super(itemView);
            constraintLayout=itemView.findViewById(R.id.container);
            imgProfile=itemView.findViewById(R.id.img_profil);
            TVScreenName=itemView.findViewById(R.id.tv_name);
            TVbody=itemView.findViewById(R.id.tv_tweet);
            tv_time=itemView.findViewById(R.id.tv_time);
            tv_like=itemView.findViewById(R.id.tv_like);
            tv_comment=itemView.findViewById(R.id.tv_comment);
            tv_retweet=itemView.findViewById(R.id.tv_retweet);
            img_post=itemView.findViewById(R.id.img_psot);
        }
    }
}
