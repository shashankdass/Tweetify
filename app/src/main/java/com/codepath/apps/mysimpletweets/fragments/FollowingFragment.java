package com.codepath.apps.mysimpletweets.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.codepath.apps.mysimpletweets.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by sdass on 8/28/16.
 */
public class FollowingFragment extends FollowFragment{
    TwitterClient twitterClient = TwitterApp.getRestClient();

    private ArrayList<Tweet> tweets;


    public static FollowingFragment newInstance(String screenName) {
        FollowingFragment followingFragment= new FollowingFragment();
        Bundle args = new Bundle();
        args.putString("screen_name",screenName);
        followingFragment.setArguments(args);
        return followingFragment;
    }

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


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvContacts.setLayoutManager(linearLayoutManager);
        rvContacts.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                TimelineFragment.since_id=1;
                populateFollowers();
            }
        });
        bindDataToAdapter();
        populateFollowers();
    }

    public void populateFollowers() {
//        miActionProgressItem.setVisible(true);
        String screenName = getArguments().getString("screen_name");
        try {
            twitterClient.getFollowingList(screenName,new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    ArrayList<User> newUsers = null;
                    try {
                        newUsers = User.fromJsonArray(response.getJSONArray("users"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    fillUpAdapterWithData(newUsers);
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
            ArrayList<User> newUsers = (ArrayList<User>) User.getAll();
            fillUpAdapterWithData(newUsers);
        }

    }
}

