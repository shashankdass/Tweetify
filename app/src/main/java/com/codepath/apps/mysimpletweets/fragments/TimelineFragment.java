package com.codepath.apps.mysimpletweets.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.mysimpletweets.activity.TweetDetailsActivity;
import com.codepath.apps.mysimpletweets.adapters.TweetsAdapter;
import com.codepath.apps.mysimpletweets.databinding.FragmentTimelineBinding;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.codepath.apps.mysimpletweets.utils.DividerItemDecoration;
import com.codepath.apps.mysimpletweets.utils.ItemClickSupport;

import org.parceler.Parcels;

import java.util.ArrayList;

public class TimelineFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    FragmentTimelineBinding fragmentTimelineBinding;
    RecyclerView rvTweets;
    protected ArrayList<Tweet> tweets;
    protected SwipeRefreshLayout swipeContainer;
    public static long since_id=1;
    public static long max_id=-1;

    TweetsAdapter tweetAdapter;
    protected OnTimelineFragmentInteractionListener listener;
    MenuItem miActionProgressItem;
    public TimelineFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnTimelineFragmentInteractionListener) {
            listener = (OnTimelineFragmentInteractionListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement MyListFragment.OnItemSelectedListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentTimelineBinding = FragmentTimelineBinding.inflate(inflater,null,true);
        rvTweets = fragmentTimelineBinding.rvTweets;
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL_LIST);
        rvTweets.addItemDecoration(itemDecoration);
        swipeContainer = fragmentTimelineBinding.swipeContainer;
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.darker_gray);
        tweets = new ArrayList<>();
        ItemClickSupport.addTo(rvTweets).setOnItemClickListener(
                new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        Intent detailsIntent = new Intent(getContext(),
                                TweetDetailsActivity.class);
                        detailsIntent.putExtra("tweet", Parcels.wrap(tweets.get(position)));
                        startActivity(detailsIntent);
                    }
                }
        );
        return fragmentTimelineBinding.getRoot();
    }
    protected void bindDataToAdapter() {
        // Bind adapter to recycler view object
        tweetAdapter = new TweetsAdapter(getContext(), tweets, getActivity().getSupportFragmentManager());
        rvTweets.setAdapter(tweetAdapter);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnTimelineFragmentInteractionListener {
        // TODO: Update argument type and name
        void onNetworkFailure();
    }

    protected void fillUpAdapterWithData(ArrayList<Tweet> newTweets) {
        tweetAdapter.addAll(newTweets);
        swipeContainer.setRefreshing(false);
        if(newTweets.size() > 0) {
            TimelineFragment.max_id = newTweets.get(newTweets.size() - 1).getUid() - 1;
            TimelineFragment.since_id = newTweets.get(0).getUid();
        }
    }

}
