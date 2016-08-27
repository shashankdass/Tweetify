package com.codepath.apps.mysimpletweets.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.adapters.TimelineFragmentPagerAdapter;
import com.codepath.apps.mysimpletweets.databinding.ActivityTimelineBinding;
import com.codepath.apps.mysimpletweets.databinding.ActivityTweetDetailsBinding;
import com.codepath.apps.mysimpletweets.fragments.HomeTimelineFragment;
import com.codepath.apps.mysimpletweets.fragments.MentionsTimelineFragment;
import com.codepath.apps.mysimpletweets.fragments.TimelineFragment;

public class TimelineActivity extends AppCompatActivity
        implements TimelineFragment.OnTimelineFragmentInteractionListener {
    HomeTimelineFragment homeTimelineFragment;
    MentionsTimelineFragment mentionsTimelineFragment;
    ActivityTimelineBinding activityTimelineBinding;
    ViewPager viewPager;
    FragmentManager fm;
    PagerSlidingTabStrip tabsStrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTimelineBinding = DataBindingUtil.setContentView(this, R.layout.activity_timeline);
        viewPager = activityTimelineBinding.viewpager;
        viewPager.setAdapter(new TimelineFragmentPagerAdapter(getSupportFragmentManager()));
        tabsStrip = activityTimelineBinding.tabs;
        tabsStrip.setViewPager(viewPager);
//        fm = getSupportFragmentManager();
//        homeTimelineFragment = HomeTimelineFragment.newInstance();
//        FragmentTransaction ft = fm.beginTransaction();
//        ft.add(homeTimelineFragment,"home_timeline_fragment").commit();
    }

    @Override
    public void onNetworkFailure() {
        Toast.makeText(this, "Internet connection is flaky!!No new tweets will be fetched!",
                Toast.LENGTH_SHORT).show();
    }
}
