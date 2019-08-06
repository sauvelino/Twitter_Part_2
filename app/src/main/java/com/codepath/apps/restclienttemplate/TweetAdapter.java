package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

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
                im.putExtra("name",tweets.get(i).user.Name);
                context.startActivity(im);
            }
        });
        Tweet tweet=tweets.get(i);
        viewHOlder.TVbody.setText(tweet.body);
        viewHOlder.TVScreenName.setText(tweet.user.ScreenName);
        String time = TimeFormatter.getTimeDifference(tweet.createdat);
        viewHOlder.tv_time.setText(time);
        viewHOlder.userrrr.setText(tweet.user.Name);
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
        public ImageView imgProfile,img_post,img_like,account_profile;
        public TextView TVScreenName,TVbody,tv_time,userrrr,tv_comment,tv_like,tv_retweet,account_user,account_followers,account_following;


        public ViewHOlder(@NonNull View itemView) {
            super(itemView);
            userrrr=itemView.findViewById(R.id.userrrr);
            account_user=itemView.findViewById(R.id.account_screen);
            account_profile=itemView.findViewById(R.id.profile);
            account_followers=itemView.findViewById(R.id.account_followers);
            account_following=itemView.findViewById(R.id.account_following);
            img_like=itemView.findViewById(R.id.img_like);
            constraintLayout=itemView.findViewById(R.id.container);
            imgProfile=itemView.findViewById(R.id.img_profil);
            TVScreenName=itemView.findViewById(R.id.nameee);
            TVbody=itemView.findViewById(R.id.tv_tweet);
            tv_time=itemView.findViewById(R.id.tv_time);
            tv_like=itemView.findViewById(R.id.tv_like);
            tv_comment=itemView.findViewById(R.id.tv_comment);
            tv_retweet=itemView.findViewById(R.id.tv_retweet);
            img_post=itemView.findViewById(R.id.img_psot);
        }
    }

    public void getDataFromDatabase(){
        TweetData data = new TweetData(context);
        Cursor cursor = data.GetData();
        while (cursor.moveToNext()){
            Log.d("cursor",cursor.getString(cursor.getColumnIndex("text")));
        }
    }

    public void saveToDatabase(){
        new DatabaseAsync().execute();
    }

    public class DatabaseAsync extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            TweetData tweetData=new TweetData(context);
            tweetData.CleanRecycler();
            for(int i=0; i<tweets.size(); i++){
                long id=tweets.get(i).uid;
                String text=tweets.get(i).body;
                String createdat=tweets.get(i).createdat;
                String name=tweets.get(i).user.Name;
                String Screen_name=tweets.get(i).user.ScreenName;
                String img_url=tweets.get(i).user.ImageUrl;
                String img_post=tweets.get(i).image;
                String favorite_count=tweets.get(i).favorite;
                String retweet_count=tweets.get(i).retweet;
                long res = tweetData.BackupTweetData(id,text,createdat,name,Screen_name,img_url,img_post,favorite_count,retweet_count);
                Log.d("BackupReponse",String.valueOf(res));
            }
            return null;
        }
    }
}
