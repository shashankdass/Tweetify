package com.codepath.apps.mysimpletweets.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.mysimpletweets.adapters.UsersAdapter;
import com.codepath.apps.mysimpletweets.databinding.FragmentFollowListBinding;
import com.codepath.apps.mysimpletweets.models.User;
import com.codepath.apps.mysimpletweets.utils.DividerItemDecoration;

import java.util.ArrayList;

/**
 * Created by sdass on 8/28/16.
 */
public class FollowFragment extends Fragment {
    FragmentFollowListBinding followListBinding;
    RecyclerView rvContacts;
    protected ArrayList<User> users;
    protected SwipeRefreshLayout swipeContainer;
    public static long since_id=1;
    public static long max_id=-1;

    UsersAdapter usersAdapter;
    protected OnFollowFragmentInteractionListener listener;
    MenuItem miActionProgressItem;
    public FollowFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.fragment_action_bar, menu);
//        miActionProgressItem = menu.findItem(R.id.miActionProgress);
//        ProgressBar v =  (ProgressBar) MenuItemCompat.getActionView(miActionProgressItem);
//    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFollowFragmentInteractionListener) {
            listener = (OnFollowFragmentInteractionListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement MyListFragment.OnItemSelectedListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        followListBinding = FragmentFollowListBinding.inflate(inflater,null,true);
        rvContacts = followListBinding.rvContact;
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL_LIST);
        rvContacts.addItemDecoration(itemDecoration);
        users = new ArrayList<>();
//        ItemClickSupport.addTo(rvContacts).setOnItemClickListener(
//                new ItemClickSupport.OnItemClickListener() {
//                    @Override
//                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
//                        Intent detailsIntent = new Intent(getContext(),
//                                TweetDetailsActivity.class);
//                        detailsIntent.putExtra("tweet", Parcels.wrap(tweets.get(position)));
//                        startActivity(detailsIntent);
//                    }
//                }
//        );
        return followListBinding.getRoot();
    }
    protected void bindDataToAdapter() {
        // Bind adapter to recycler view object
        usersAdapter = new UsersAdapter(users);
        rvContacts.setAdapter(usersAdapter);
    }



    protected void fillUpAdapterWithData(ArrayList<User> users) {
        usersAdapter.addAll(users);
//        swipeContainer.setRefreshing(false);
//        if(newTweets.size() > 0) {
//            TimelineFragment.max_id = newTweets.get(newTweets.size() - 1).getUid() - 1;
//            TimelineFragment.since_id = newTweets.get(0).getUid();
//        }
    }
    public interface OnFollowFragmentInteractionListener {
        // TODO: Update argument type and name
        void onNetworkFailure();
    }
}
