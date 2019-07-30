package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Tweet {
    public String body,createdat;
    public long uid;
    public String type;
    public String image;
    public String retweet,favorite;
    public User user;


    public static Tweet fromJson(JSONObject jsonObject) throws JSONException {
        Tweet tweet=new Tweet();

            tweet.body=jsonObject.getString("text");
            tweet.uid=jsonObject.getLong("id");
            tweet.createdat=jsonObject.getString("created_at");
            tweet.user=User.fromJson(jsonObject.getJSONObject("user"));
            tweet.retweet=jsonObject.getString("retweet_count");
            tweet.favorite=jsonObject.getString("favorite_count");
            if(jsonObject.getJSONObject("entities").has("media")){
                tweet.image = jsonObject.getJSONObject("entities").getJSONArray("media").getJSONObject(0).getString("media_url");
                tweet.type = jsonObject.getJSONObject("entities").getJSONArray("media").getJSONObject(0).getString("type");
            }else{
                tweet.type = "";
                tweet.image = "";
            }





        return tweet;
    }
}
