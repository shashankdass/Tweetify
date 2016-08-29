package com.codepath.apps.mysimpletweets.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.mysimpletweets.NetworkFailure;
import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TwitterApp;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.databinding.ActivityProfileBinding;
import com.codepath.apps.mysimpletweets.fragments.TimelineFragment;
import com.codepath.apps.mysimpletweets.fragments.UserTimelineFragment;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.codepath.apps.mysimpletweets.models.User;
import com.codepath.apps.mysimpletweets.utils.PatternEditableBuilder;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.regex.Pattern;

import cz.msebera.android.httpclient.Header;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;


public class ProfileActivity extends AppCompatActivity
        implements TimelineFragment.OnTimelineFragmentInteractionListener{
    ActivityProfileBinding binding;
    TwitterClient twitterClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.ll_toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.twitter_logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        twitterClient = TwitterApp.getRestClient();
        Tweet tweet = Parcels.unwrap(getIntent().getParcelableExtra("tweet"));
        if(tweet == null) {
            try {
                twitterClient.getUserInfo(new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        User user = User.fromJSON(response);
                        getSupportActionBar().setTitle("@" + user.getScreenName());
                        populateProfileHeader(user);

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                    }
                });
            } catch (NetworkFailure networkFailure) {
                networkFailure.printStackTrace();
            }
        } else {
            getSupportActionBar().setTitle("@" +tweet.getUser().getScreenName());
            populateProfileHeader(tweet.getUser());
        }
        if(savedInstanceState == null) {
            String screenName = "";
            if (tweet != null)
                screenName = tweet.getUser().getScreenName();
            UserTimelineFragment userTimelineFragment = UserTimelineFragment.newInstance(screenName);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(binding.flContainer.getId(), userTimelineFragment);
            ft.commit();
        }
    }

    private void populateProfileHeader(User user) {
        final String screenName = user.getScreenName();
        Picasso.with(this).load(user.getProfileImageURL()).
                transform(new RoundedCornersTransformation(2,2)).into(binding.ivProfileImage);
        binding.tvName.setText(user.getName());
        binding.tvTagline.setText("@"+user.getScreenName());
        TextView tvFollowers = binding.tvFollowers;
        tvFollowers.setText(user.getFollowers()+" Followers");
        new PatternEditableBuilder().
                addPattern(Pattern.compile("\\d*(Followers)"), R.color.twitter_blue,
                        new PatternEditableBuilder.SpannableClickedListener() {
                            @Override
                            public void onSpanClicked(String text) {
                                Intent i = new Intent(ProfileActivity.this,ContactsActivity.class);
                                i.putExtra("user_screen_name",screenName);
                                i.putExtra("followers",true);
                                i.putExtra("following",false);
                                startActivity(i);
                            }
                        }).into(tvFollowers);


        TextView tvFollowing = binding.tvFollowing;
        tvFollowing.setText(user.getFollowing()+" Following");
        new PatternEditableBuilder().
                addPattern(Pattern.compile("\\d*(Following)"), R.color.twitter_blue,
                        new PatternEditableBuilder.SpannableClickedListener() {
                            @Override
                            public void onSpanClicked(String text) {
                                Intent i = new Intent(ProfileActivity.this,ContactsActivity.class);
                                i.putExtra("user_screen_name",screenName);
                                i.putExtra("followers",false);
                                i.putExtra("following",true);
                                startActivity(i);
                            }
                        }).into(tvFollowing);
        binding.tvTagline.setText(user.getTagline());
    }

    @Override
    public void onNetworkFailure() {
        Toast.makeText(this, "Internet connection is flaky!!No new tweets will be fetched!",
                Toast.LENGTH_SHORT).show();
    }

}
