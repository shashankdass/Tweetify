package com.codepath.apps.mysimpletweets.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.adapters.TimelineFragmentPagerAdapter;
import com.codepath.apps.mysimpletweets.databinding.ActivityTimelineBinding;
import com.codepath.apps.mysimpletweets.dialogs.ComposeDialog;
import com.codepath.apps.mysimpletweets.fragments.SearchFragment;
import com.codepath.apps.mysimpletweets.fragments.TimelineFragment;

public class TimelineActivity extends AppCompatActivity
        implements TimelineFragment.OnTimelineFragmentInteractionListener {
    ActivityTimelineBinding activityTimelineBinding;
    ViewPager viewPager;
    PagerSlidingTabStrip tabsStrip;
    SearchFragment searchFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTimelineBinding = DataBindingUtil.setContentView(this, R.layout.activity_timeline);
        Toolbar toolbar = (Toolbar) findViewById(R.id.ll_toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.twitter_logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        viewPager = activityTimelineBinding.viewpager;
        viewPager.setAdapter(new TimelineFragmentPagerAdapter(getSupportFragmentManager()));
        tabsStrip = activityTimelineBinding.tabs;
        tabsStrip.setViewPager(viewPager);
    }
//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//        // Store instance of the menu item containing progress
//        miActionProgressItem = menu.findItem(R.id.miActionProgress);
//        // Extract the action-view from the menu item
//        ProgressBar v =  (ProgressBar) MenuItemCompat.getActionView(miActionProgressItem);
//        // Return to finish
//        return super.onPrepareOptionsMenu(menu);
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_timeline, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // perform query here
                Intent searchIntent = new Intent(getBaseContext(),SearchTweetActivity.class);
                searchIntent.putExtra("query",query);
                startActivity(searchIntent);
                // workaround to avoid issues with some emulators and keyboard devices firing twice if a keyboard enter is used
                // see https://code.google.com/p/android/issues/detail?id=24599
                searchView.clearFocus();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public void onNetworkFailure() {
        Toast.makeText(this, "Internet connection is flaky!!No new tweets will be fetched!",
                Toast.LENGTH_SHORT).show();
    }


    public void onProfileView(MenuItem item) {
        Intent profileIntent = new Intent(this, ProfileActivity.class);
        startActivity(profileIntent);
    }

    public void showComposeFragment(MenuItem item) {
        ComposeDialog composeDialog = ComposeDialog.newInstance();
        composeDialog.setmListener(new ComposeDialog.OnTweetListener() {

            @Override
            public void onSucessfulTweet() {
                TimelineFragment.since_id = 1;
                TimelineFragment.max_id=-1;
            }
        });
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(composeDialog,"Compose").commit();
    }
}
