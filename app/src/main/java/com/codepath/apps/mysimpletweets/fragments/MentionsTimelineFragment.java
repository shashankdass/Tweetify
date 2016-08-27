package com.codepath.apps.mysimpletweets.fragments;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.mysimpletweets.NetworkFailure;
import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TwitterApp;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.listener.EndlessRecyclerViewScrollListener;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MentionsTimelineFragment extends TimelineFragment {
    TwitterClient twitterClient;
    private ArrayList<Tweet> tweets;

    public MentionsTimelineFragment() {
    }

    public static MentionsTimelineFragment newInstance() {
        MentionsTimelineFragment mentionsTimelineFragment = new MentionsTimelineFragment();
        return mentionsTimelineFragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        twitterClient = TwitterApp.getRestClient();

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                TimelineFragment.max_id = -1;
                populateTimeline();
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvTweets.setLayoutManager(linearLayoutManager);
        rvTweets.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                TimelineFragment.since_id=1;
                populateTimeline();
            }
        });
        bindDataToAdapter();
        populateTimeline();
        View view = inflater.inflate(R.layout.fragment_timeline, container, false);

        return view;
    }
    public void populateTimeline() {
        try {
            twitterClient.getMentionsTimeline(new JsonHttpResponseHandler() {
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
            }, TimelineFragment.max_id, TimelineFragment.since_id);
        } catch (NetworkFailure networkFailure) {
            listener.onNetworkFailure();
            ArrayList<Tweet> newTweets = (ArrayList<Tweet>) Tweet.getAll();
            setAdapter(newTweets);
        }

    }

}