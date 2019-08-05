package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

@Parcel
public class Tweet {
    public String body,createdat;
    public long uid;
    public String type;
    public String image;
    public String retweet,favorite;
    public User user;

    public Tweet() {
    }

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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getCreatedat() {
        return createdat;
    }

    public void setCreatedat(String createdat) {
        this.createdat = createdat;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRetweet() {
        return retweet;
    }

    public void setRetweet(String retweet) {
        this.retweet = retweet;
    }

    public String getFavorite() {
        return favorite;
    }

    public void setFavorite(String favorite) {
        this.favorite = favorite;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
