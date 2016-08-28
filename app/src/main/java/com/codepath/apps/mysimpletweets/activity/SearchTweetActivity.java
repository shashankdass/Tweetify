package com.codepath.apps.mysimpletweets.activity;

import android.databinding.DataBindingUtil;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TwitterApp;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.databinding.ActivitySearchBinding;
import com.codepath.apps.mysimpletweets.fragments.SearchFragment;
import com.codepath.apps.mysimpletweets.fragments.TimelineFragment;

public class SearchTweetActivity extends AppCompatActivity  implements TimelineFragment.OnTimelineFragmentInteractionListener{
    ActivitySearchBinding binding;
    TwitterClient twitterClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.twitter_logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setTitle("Search Results");
        twitterClient = TwitterApp.getRestClient();
        String query = getIntent().getStringExtra("query");
        if(savedInstanceState == null) {
            SearchFragment userTimelineFragment = SearchFragment.newInstance(query);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(binding.searchContainer.getId(), userTimelineFragment);
            ft.commit();
        }
    }


    @Override
    public void onNetworkFailure() {
        Toast.makeText(this, "Internet connection is flaky!!No new tweets will be fetched!",
                Toast.LENGTH_SHORT).show();
    }
}
