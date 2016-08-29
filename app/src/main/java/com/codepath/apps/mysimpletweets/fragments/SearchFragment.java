package com.codepath.apps.mysimpletweets.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.mysimpletweets.NetworkFailure;
import com.codepath.apps.mysimpletweets.TwitterApp;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.listener.EndlessRecyclerViewScrollListener;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by sdass on 8/27/16.
 */
public class SearchFragment extends TimelineFragment {
    TwitterClient twitterClient = TwitterApp.getRestClient();

    private ArrayList<Tweet> tweets;


    public static SearchFragment newInstance(String query) {
        SearchFragment searchFragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString("query",query);
        searchFragment.setArguments(args);
        return searchFragment;
    }
//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.fragment_action_bar, menu);
//        miActionProgressItem = menu.findItem(R.id.miActionProgress);
//        // Extract the action-view from the menu item
//        ProgressBar v =  (ProgressBar) MenuItemCompat.getActionView(miActionProgressItem);
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


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
    }

    public void populateTimeline() {
//        miActionProgressItem.setVisible(true);
        String query = getArguments().getString("query");
        try {
            twitterClient.getSearchResults(query,new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    ArrayList<Tweet> newTweets = null;
                    try {
                        newTweets = Tweet.fromJsonArray(response.getJSONArray("statuses"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    fillUpAdapterWithData(newTweets);
//                    miActionProgressItem.setVisible(false);

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Log.d("DEBUG", errorResponse.toString());
//                    miActionProgressItem.setVisible(false);
                }
            });
        } catch (NetworkFailure networkFailure) {
            listener.onNetworkFailure();
            ArrayList<Tweet> newTweets = (ArrayList<Tweet>) Tweet.getAll();
            fillUpAdapterWithData(newTweets);
        }

    }
}
