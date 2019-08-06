package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

@Parcel
public class User {
    public String Name,ScreenName;
    public long uid;
    public String ImageUrl;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getScreenName() {
        return ScreenName;
    }

    public void setScreenName(String screenName) {
        ScreenName = screenName;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public User() {
    }

    public static User fromJson(JSONObject jsonObject) throws JSONException {
        User user=new User();
user.Name=jsonObject.getString("name");
user.uid=jsonObject.getLong("id");
user.ScreenName=jsonObject.getString("screen_name");
user.ImageUrl=jsonObject.getString("profile_image_url");



        return user;
    }
}
