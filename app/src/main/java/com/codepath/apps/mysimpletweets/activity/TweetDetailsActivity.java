package com.codepath.apps.mysimpletweets.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TwitterApp;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.databinding.ActivityTweetDetailsBinding;
import com.codepath.apps.mysimpletweets.dialogs.ReplyDialog;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class TweetDetailsActivity extends AppCompatActivity {
    ActivityTweetDetailsBinding binding;
    Tweet tweet;
    TwitterClient twitterClient;
    ReplyDialog replyDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        twitterClient = TwitterApp.getRestClient();
        binding  = DataBindingUtil.setContentView(this,R.layout.activity_tweet_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.ll_toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.twitter_logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        tweet = Parcels.unwrap(getIntent().getParcelableExtra("tweet"));
        Picasso.with(this).load(tweet.getUser().getProfileImageURL()).
                transform(new RoundedCornersTransformation(2,2)).into(binding.ibtnThumbnail);
        binding.tvHandle.setText(tweet.getUser().getName());
        binding.tvHandleName.setText("@"+tweet.getUser().getScreenName());
        binding.etMainTweet.setText(tweet.getBody());
        binding.tvNumOfRetweets.setText(tweet.getRetweet_count());
        binding.tvNumOfLikes.setText(tweet.getFavorites_count());

        if (tweet.getMedia_url() != null) {
            Picasso.with(this).load(tweet.getMedia_url()).
                    transform(new RoundedCornersTransformation(5, 5))
                    .into(binding.ivMainImage);
        }
        binding.ivReply.setImageResource(R.drawable.reply);
        if (Integer.parseInt(tweet.getRetweet_count()) > 0)
            binding.ivRetweet.setImageResource(R.drawable.retweet_pressed);
        else
            binding.ivRetweet.setImageResource(R.drawable.retweet);
        binding.ivStar.setImageResource(0);

        if (Integer.parseInt(tweet.getFavorites_count()) == 0) {
            binding.ivStar.setImageResource(R.drawable.star_icon_plain);
        } else {
            binding.ivStar.setImageResource(R.drawable.fav_icon);
        }
        binding.ivRetweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int new_retweet_count = Integer.parseInt(tweet.getRetweet_count())+1;
                tweet.setRetweet_count(""+new_retweet_count);
                binding.tvNumOfRetweets.setText(""+new_retweet_count);
                twitterClient = TwitterApp.getRestClient();
                twitterClient.retweet(new JsonHttpResponseHandler(){

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                    }


                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                    }
                },tweet.getUid());

            }
        });
        binding.ivStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.parseInt(tweet.getFavorites_count()) >= 0) {
                    binding.ivStar.setImageResource(0);
                    binding.ivStar.setImageResource(R.drawable.fav_icon);
                }
                int new_likes_count = Integer.parseInt(tweet.getFavorites_count())+1;
                tweet.setFavorites_count(""+new_likes_count);
                binding.tvNumOfLikes.setText(""+new_likes_count);
                twitterClient = TwitterApp.getRestClient();
                twitterClient.createFavorite(new JsonHttpResponseHandler(){

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                    }


                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                    }
                },tweet.getUid());
            }
        });
        binding.ivReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showReplyFragment();
            }
        });


    }
    private void showReplyFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        replyDialog = ReplyDialog.newInstance(tweet);
        ft.add(replyDialog,"Reply").commit();
    }
}
