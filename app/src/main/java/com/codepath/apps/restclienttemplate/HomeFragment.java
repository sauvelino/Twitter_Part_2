package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class HomeFragment extends Fragment {
    private static final int RESULT_OK =20 ;
    private  TweetAdapter tweetAdapter;
    private List<Tweet> tweets;
    private TwitterClient client;
    private SwipeRefreshLayout swipeRefreshLayout;

    // Store instance variables
    private String title;
    private int page;
    private int Request_code=20;
    ProgressBar progressBar;

    // newInstance constructor for creating fragment with arguments
    public static HomeFragment newInstance(int page, String title) {
        HomeFragment fragmentFirst = new HomeFragment();
        Bundle args = new Bundle();
       // args.putInt("someInt", page);
       // args.putString("someTitle", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        page = getArguments().getInt("someInt", 0);
   //     title = getArguments().getString("someTitle");

        client= TwitterApplication.getRestClient(getActivity());

        PopulateHomeTimeLIne();
    }

    private void GetOfflineData() {
        TweetData database = new TweetData(getActivity());
        Cursor cursor=database.GetData();
        if(cursor.getCount()!=0){
            List<Tweet> tweets=new ArrayList<>();
            while (cursor.moveToNext()){

                Tweet tweet=new Tweet();
                tweet.setBody(cursor.getString(cursor.getColumnIndex("text")));
                Log.e("Erreur",cursor.getString(cursor.getColumnIndex("createAt")));
                tweet.setUid(cursor.getLong(cursor.getColumnIndex("id")));
                tweet.setCreatedat(cursor.getString(cursor.getColumnIndex("createAt")));
                tweets.add(tweet);
            }
            tweetAdapter.clear();
            tweetAdapter.addTweet(tweets);
        }
    }

    private void PopulateHomeTimeLIne() {
     client.getHomeTimeLine(new JsonHttpResponseHandler(){

         @Override
         public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

             Log.d("TwitterClient",response.toString());
             List<Tweet> tweettoadd=new ArrayList<>();
             for(int i=0; i<response.length();i++){
                 try {
                     //convert each jsonobject into tweet object
                     JSONObject jsonObject= response.getJSONObject(i);

                     Tweet tweet= Tweet.fromJson(jsonObject);
                     //add the tweet into our data source
                     tweettoadd.add(tweet);

                     //notify datachange
                     //  tweetAdapter.notifyItemInserted(tweets.size()-1);
                 } catch (JSONException e) {
                     e.printStackTrace();
                 }
             }
             //clear the existing data
             tweetAdapter.clear();

             //add the new data
             tweetAdapter.addTweet(tweettoadd);
             tweetAdapter.saveToDatabase();

            swipeRefreshLayout.setRefreshing(false);
         }
                                @Override
                                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                                    //GetOfflineData();
                                }
                            }


     );
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==Request_code && resultCode==RESULT_OK){
            Tweet tweet= Parcels.unwrap(data.getParcelableExtra("tweet"));
            tweets.add(0,tweet);
            tweetAdapter.notifyItemInserted(0);

        }
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_main, container, false);
        progressBar=view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        RecyclerView RecyclerView =view.findViewById(R.id.rv_list);
        tweets=new ArrayList<>();
        tweetAdapter=new TweetAdapter(getActivity(),tweets);
        RecyclerView.setAdapter(tweetAdapter);
        RecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        RecyclerView.smoothScrollToPosition(0);
        tweetAdapter.notifyItemInserted(0);
       //swipe
        swipeRefreshLayout= view.findViewById(R.id.swipecontainer);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                PopulateHomeTimeLIne();
                Log.d("TwitterClient","Refresh");
            }
        });

        return view;
    }


}
