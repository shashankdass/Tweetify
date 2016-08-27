package com.codepath.apps.mysimpletweets.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.codepath.apps.mysimpletweets.fragments.HomeTimelineFragment;
import com.codepath.apps.mysimpletweets.fragments.MentionsTimelineFragment;
import com.codepath.apps.mysimpletweets.fragments.TimelineFragment;

public class TimelineActivity extends AppCompatActivity
        implements TimelineFragment.OnTimelineFragmentInteractionListener {
    HomeTimelineFragment homeTimelineFragment;
    MentionsTimelineFragment mentionsTimelineFragment;
    FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fm = getSupportFragmentManager();
        mentionsTimelineFragment = MentionsTimelineFragment.newInstance();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(mentionsTimelineFragment,"home_timeline_fragment").commit();
    }

    @Override
    public void onNetworkFailure() {
        Toast.makeText(this, "Internet connection is flaky!!No new tweets will be fetched!",
                Toast.LENGTH_SHORT).show();
    }
}
