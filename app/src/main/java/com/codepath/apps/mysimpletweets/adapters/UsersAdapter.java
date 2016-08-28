package com.codepath.apps.mysimpletweets.adapters;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.mysimpletweets.BR;
import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TwitterApp;
import com.codepath.apps.mysimpletweets.activity.ProfileActivity;
import com.codepath.apps.mysimpletweets.databinding.ContactRowLayoutBinding;
import com.codepath.apps.mysimpletweets.databinding.TweetRowLayoutBinding;
import com.codepath.apps.mysimpletweets.databinding.TweetRowLayoutImageBinding;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.codepath.apps.mysimpletweets.models.User;
import com.codepath.apps.mysimpletweets.utils.ParseRelativeDate;
import com.codepath.apps.mysimpletweets.view.TweetViewHolder;
import com.codepath.apps.mysimpletweets.view.TweetViewHolderWithImage;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.List;

import cz.msebera.android.httpclient.Header;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by sdass on 8/28/16.
 */
    public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ContactViewHolder> {
        private List<User> mUsers;

        public static class ContactViewHolder extends RecyclerView.ViewHolder {
            private ContactRowLayoutBinding binding;
            User user;
            Context mContext;
            public ContactViewHolder(Context context, ContactRowLayoutBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
                this.mContext = context;
            }
            public void bindUser(final User user) {
                binding.tvName.setText(user.getName());
                binding.tvTagline.setText(user.getScreenName());
                Picasso.with(mContext).load(user.getProfileImageURL()).
                        transform(new RoundedCornersTransformation(2,2)).into(binding.ivProfileImage);
            }

            public ViewDataBinding getBinding() {
                return binding;
            }
        }

        public UsersAdapter(List<User> recyclerUsers) {
            this.mUsers = recyclerUsers;
        }

        @Override
        public ContactViewHolder onCreateViewHolder(ViewGroup parent, int type) {
            ContactViewHolder viewHolder;
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            ContactRowLayoutBinding contactRowLayoutBinding = DataBindingUtil
                    .inflate(inflater, R.layout.contact_row_layout, parent, false);
            viewHolder = new ContactViewHolder(parent.getContext(),contactRowLayoutBinding);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ContactViewHolder holder, int position) {
            final User user = mUsers.get(position);
            holder.bindUser(user);
        }

        @Override
        public int getItemCount() {
            return mUsers.size();
        }
        public void clear() {
            mUsers.clear();
            notifyDataSetChanged();
        }
        // Add a list of items
        public void addAll(List<User> list) {
        mUsers.addAll(0,list);
        notifyDataSetChanged();
    }
    }



