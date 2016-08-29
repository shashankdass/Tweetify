package com.codepath.apps.mysimpletweets.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TwitterApp;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.databinding.FollowListBinding;
import com.codepath.apps.mysimpletweets.fragments.FollowFragment;
import com.codepath.apps.mysimpletweets.fragments.FollowersFragment;
import com.codepath.apps.mysimpletweets.fragments.FollowingFragment;

public class ContactsActivity extends AppCompatActivity implements FollowFragment.OnFollowFragmentInteractionListener{

     FollowListBinding binding;
    TwitterClient twitterClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.follow_list);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.ll_toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.twitter_logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        twitterClient = TwitterApp.getRestClient();
        String screenName = getIntent().getStringExtra("user_screen_name");
        Boolean followers = getIntent().getBooleanExtra("followers",false);
        Boolean following = getIntent().getBooleanExtra("following",false);
        if(savedInstanceState == null) {
            if(followers) {
                FollowersFragment followersFragment = FollowersFragment.newInstance(screenName);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(binding.flContactsContainer.getId(), followersFragment);
                ft.commit();
            } else if(following) {
                FollowingFragment followingFragment = FollowingFragment.newInstance(screenName);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(binding.flContactsContainer.getId(), followingFragment);
                ft.commit();
            }
        }
    }

    @Override
    public void onNetworkFailure() {
        Toast.makeText(this, "Internet connection is flaky!!No new tweets will be fetched!",
                Toast.LENGTH_SHORT).show();
    }
}
