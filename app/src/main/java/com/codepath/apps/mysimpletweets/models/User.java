package com.codepath.apps.mysimpletweets.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

/**
 "user": {
 "profile_sidebar_fill_color": "DDEEF6",
 "profile_sidebar_border_color": "C0DEED",
 "profile_background_tile": false,
 "name": "Twitter API",
 "profile_image_url":"http://a0.twimg.com/profile_images/2284174872/7df3h38zabcvjylnyfe3_normal.png",
 "created_at": "Wed May 23 06:01:13 +0000 2007",
 "location": "San Francisco, CA",
 "follow_request_sent": false,
 "profile_link_color": "0084B4",
 "is_translator": false,
 "id_str": "6253282",
 "entities": {
 "url": {
 "urls": [
 {
 "expanded_url": null,
 "url": "http://dev.twitter.com",
 "indices": [
 0,
 22
 ]
 }
 ]
 },
 "description": {
 "urls": [

 ]
 }
 },
 "default_profile": true,
 "contributors_enabled": true,
 "favourites_count": 24,
 "url": "http://dev.twitter.com",
 "profile_image_url_https":"https://si0.twimg.com/profile_images/2284174872/7df3h38zabcvjylnyfe3_normal.png",
 "utc_offset": -28800,
 "id": 6253282,
 "profile_use_background_image": true,
 "listed_count": 10775,
 "profile_text_color": "333333",
 "lang": "en",
 "followers_count": 1212864,
 "protected": false,
 "notifications": null,
 "profile_background_image_url_https":"https://si0.twimg.com/images/themes/theme1/bg.png",
 "profile_background_color": "C0DEED",
 "verified": true,
 "geo_enabled": true,
 "time_zone": "Pacific Time (US & Canada)",
 "description": "The Real Twitter API. I tweet about API changes, service issues and happily answer questions about Twitter and our API. Don't get an answer? It's on my website.",
 "default_profile_image": false,
 "profile_background_image_url":"http://a0.twimg.com/images/themes/theme1/bg.png",
 "statuses_count": 3333,
 "friends_count": 31,
 "following": null,
 "show_all_inline_media": false,
 "screen_name": "twitterapi"
 }
 */

@Table(name = "users")
@Parcel(analyze={User.class})
public class User extends Model {
    @Column(name = "remote_id", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public long remoteId;
    @Column(name = "Name")
    private String name;
    @Column(name = "Uid")
    private Long uid;
    @Column(name = "ScreenName")
    private String screenName;
    @Column(name = "ProfileImageURL")
    private String profileImageURL;
    @Column(name = "Followers")
    private String followers;
    @Column(name = "Following")
    private String following;
    @Column(name = "Tagline")
    private String tagline;

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }



    public String getFollowing() {
        return following;
    }

    public void setFollowing(String following) {
        this.following = following;
    }

    public String getFollowers() {
        return followers;
    }

    public void setFollowers(String followers) {
        this.followers = followers;
    }



    public String getName() {
        return name;
    }

    public Long getUid() {
        return uid;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getProfileImageURL() {
        return profileImageURL;
    }


    public static User fromJSON(JSONObject json){
        User u = new User();
        try {
            u.name = json.getString("name");
            u.uid = json.getLong("id");
            u.screenName = json.getString("screen_name");
            u.profileImageURL = json.getString("profile_image_url");
            u.followers = json.getString("followers_count");
            u.following = json.getString("friends_count");
            u.tagline = json.getString("description");
            u.save();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return u;
    }
    public static ArrayList<User> fromJsonArray(JSONArray jsonArray) {
        ArrayList<User> users = new ArrayList<>();
        for (int i=0; i<jsonArray.length();i++) {
            try {
                User user = User.fromJSON(jsonArray.getJSONObject(i));
                if(user != null) {
                    users.add(user);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }
        }
        return users;
    }
    public static List<User> getAll() {
        // This is how you execute a query
        return new Select()
                .from(User.class)
                .orderBy("Uid ASC")
                .execute();
    }

}
