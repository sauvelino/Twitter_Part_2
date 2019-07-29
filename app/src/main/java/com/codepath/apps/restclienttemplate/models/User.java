package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
    public String Name,ScreenName;
    public long uid;
    public String ImageUrl;

    public static User fromJson(JSONObject jsonObject) throws JSONException {
        User user=new User();
user.Name=jsonObject.getString("name");
user.uid=jsonObject.getLong("id");
user.ScreenName=jsonObject.getString("screen_name");
user.ImageUrl=jsonObject.getString("profile_image_url");



        return user;
    }
}
