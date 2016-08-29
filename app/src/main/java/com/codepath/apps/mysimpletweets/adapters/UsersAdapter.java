package com.codepath.apps.mysimpletweets.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.databinding.ContactRowLayoutBinding;
import com.codepath.apps.mysimpletweets.models.User;
import com.squareup.picasso.Picasso;

import java.util.List;

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



