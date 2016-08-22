package com.codepath.apps.mysimpletweets.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.codepath.apps.mysimpletweets.NetworkFailure;
import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TwitterApp;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.adapters.TweetsAdapter;
import com.codepath.apps.mysimpletweets.databinding.ActivityTimelineBinding;
import com.codepath.apps.mysimpletweets.listener.EndlessRecyclerViewScrollListener;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.codepath.apps.mysimpletweets.utils.ItemClickSupport;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.ArrayList;
import cz.msebera.android.httpclient.Header;

public class TimelineActivity extends AppCompatActivity{
    TwitterClient twitterClient;
    private ArrayList<Tweet> tweets;
    ActivityTimelineBinding binding;
    RecyclerView rvTweets;
    TweetsAdapter tweetAdapter;
    Toolbar toolbar;
    ImageView ivCompose;
    private SwipeRefreshLayout swipeContainer;
    static long since_id=1;
    static long max_id=-1;
    ComposeFragment composeFragment;
    FragmentManager fm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_timeline);
        tweets = new ArrayList<>();
        toolbar = binding.toolbar;
        rvTweets = binding.rvTweets;
        fm = getSupportFragmentManager();

        ItemClickSupport.addTo(rvTweets).setOnItemClickListener(
                new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        Intent detailsIntent = new Intent(getApplicationContext(),
                                TweetDetailsActivity.class);
                        detailsIntent.putExtra("tweet", Parcels.wrap(tweets.get(position)));
                        startActivity(detailsIntent);
                    }
                }
        );

        ivCompose = binding.ivCompose;
        swipeContainer = binding.swipeContainer;
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                    TimelineActivity.max_id = -1;
                    populateTimeline();
            }
        });
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.darker_gray);
        setSupportActionBar(toolbar);
        bindDataToAdapter();
        twitterClient = TwitterApp.getRestClient();
        populateTimeline();
//        ivCompose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(TimelineActivity.this,ComposeActivity.class);
//                startActivity(intent);
//
//            }
//        });
        ivCompose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showComposeFragment();
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        twitterClient.clearAccessToken();
    }


    private void showComposeFragment() {
        composeFragment = ComposeFragment.newInstance();
        composeFragment.setmListener(new ComposeFragment.OnTweetListener() {

            @Override
            public void onSucessfulTweet() {
                TimelineActivity.since_id = 1;
                TimelineActivity.max_id=-1;
                populateTimeline();
            }
        });
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(composeFragment,"Compose").commit();
    }


    private void bindDataToAdapter() {
        // Bind adapter to recycler view object
        tweetAdapter = new TweetsAdapter(this, tweets, fm);
        rvTweets.setAdapter(tweetAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvTweets.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                TimelineActivity.since_id=1;
                populateTimeline();
            }
        });
        rvTweets.setLayoutManager(linearLayoutManager);

    }

    private void populateTimeline() {
        try {
            twitterClient.getHomeTimeline(new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
//                    tweetAdapter.clear();
                    ArrayList<Tweet> newTweets = Tweet.fromJsonArray(response);
                    setAdapter(newTweets);

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Log.d("DEBUG", errorResponse.toString());
                }
            }, TimelineActivity.max_id, TimelineActivity.since_id);
        } catch (NetworkFailure networkFailure) {
            showNetworkFailureDialogBox();
            ArrayList<Tweet> newTweets = (ArrayList<Tweet>) Tweet.getAll();
            setAdapter(newTweets);
        }

    }

    private void setAdapter(ArrayList<Tweet> newTweets) {
        tweetAdapter.addAll(newTweets);
        swipeContainer.setRefreshing(false);
        if(newTweets.size() > 0) {
            TimelineActivity.max_id = newTweets.get(newTweets.size() - 1).getUid() - 1;
            TimelineActivity.since_id = newTweets.get(0).getUid();
        }
    }

    private void showNetworkFailureDialogBox() {
        Toast.makeText(this, "Internet connection is flaky!!No new tweets will be fetched!",
                Toast.LENGTH_SHORT).show();
    }


}
