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
 * [
 {
 "coordinates": null,
 "favorited": false,
 "truncated": false,
 "created_at": "Wed Aug 29 17:12:58 +0000 2012",
 "id_str": "240859602684612608",
 "entities": {
 "urls": [
 {
 "expanded_url": "https://dev.twitter.com/blog/twitter-certified-products",
 "url": "https://t.co/MjJ8xAnT",
 "indices": [
 52,
 73
 ],
 "display_url": "dev.twitter.com/blog/twitter-c\u2026"
 }
 ],
 "hashtags": [

 ],
 "user_mentions": [

 ]
 },
 "in_reply_to_user_id_str": null,
 "contributors": null,
 "text": "Introducing the Twitter Certified Products Program: https://t.co/MjJ8xAnT",
 "retweet_count": 121,
 "in_reply_to_status_id_str": null,
 "id": 240859602684612608,
 "geo": null,
 "retweeted": false,
 "possibly_sensitive": false,
 "in_reply_to_user_id": null,
 "place": null,
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
 "favorites_count": 24,
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
 },
 "in_reply_to_screen_name": null,
 "source": "<a href="//sites.google.com/site/yorufukurou/%5C%22" rel="\"nofollow\"">YoruFukurou</a>",
 "in_reply_to_status_id": null
 },
  {
  }
  ]
 */
@Parcel(analyze={Tweet.class})
@Table(name = "tweets")
public class Tweet extends Model {
    @Column(name = "remote_id", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public long remoteId;
    @Column(name = "Body")
    private String body;
    @Column(name = "Uid")
    private long uid; // unique ID
    @Column(name = "User", onUpdate = Column.ForeignKeyAction.CASCADE, onDelete = Column.ForeignKeyAction.CASCADE)
    private User user;
    @Column(name = "CreatedAt")
    private String createdAt;
    @Column(name = "RetweetCount")
    private String retweet_count;
    @Column(name = "FavoritesCount")
    private String favorites_count;
    @Column(name = "MediaUrl")
    private String media_url;

    public String getMedia_url() {
        return media_url;
    }

    public void setMedia_url(String media_url) {
        this.media_url = media_url;
    }



    public Tweet() {
        super();
    }
    public static List<Tweet> getAll() {
        // This is how you execute a query
        return new Select()
                .from(Tweet.class)
                .orderBy("Uid ASC")
                .execute();
    }


    public void setRetweet_count(String retweet_count) {
        this.retweet_count = retweet_count;
    }

    public void setFavorites_count(String favorites_count) {
        this.favorites_count = favorites_count;
    }



    public String getRetweet_count() {
        return retweet_count;
    }

    public String getFavorites_count() {
        return favorites_count;
    }

    public String getBody() {
        return body;
    }

    public long getUid() {
        return uid;
    }

    public User getUser() {
        return user;
    }

    public String getCreatedAt() {
        return createdAt;
    }



    public static Tweet fromJson(JSONObject object) {
        Tweet tweet = new Tweet();
        try {
            tweet.body = object.getString("text");
            tweet.uid = object.getLong("id");
            tweet.createdAt = object.getString("created_at");
            tweet.user = User.fromJSON(object.getJSONObject("user"));
            tweet.favorites_count = object.getString("favorite_count");
            tweet.retweet_count = object.getString("retweet_count");
            JSONArray mediaArray = object.getJSONObject("entities").getJSONArray("media");
            if(mediaArray != null && mediaArray.length() > 0)
                tweet.media_url = mediaArray.getJSONObject(0).getString("media_url");
            tweet.save();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return tweet;
    }

    public static ArrayList<Tweet> fromJsonArray(JSONArray jsonArray) {
        ArrayList<Tweet> tweets = new ArrayList<>();
        for (int i=0; i<jsonArray.length();i++) {
            try {
                Tweet tweet = Tweet.fromJson(jsonArray.getJSONObject(i));
                if(tweet != null) {
                    tweets.add(tweet);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }
        }
        return tweets;
    }
}
