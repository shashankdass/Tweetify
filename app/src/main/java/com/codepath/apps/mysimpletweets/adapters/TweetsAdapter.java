package com.codepath.apps.mysimpletweets.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.databinding.TweetRowLayoutBinding;
import com.codepath.apps.mysimpletweets.databinding.TweetRowLayoutImageBinding;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.codepath.apps.mysimpletweets.view.TweetViewHolder;
import com.codepath.apps.mysimpletweets.view.TweetViewHolderWithImage;

import java.util.List;

/**
 * Created by sdass on 8/17/16.
 */
public class TweetsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    TweetRowLayoutBinding binding;
    private Context mContext;
    private List<Tweet> mTweets;
    private FragmentManager mFm;
    public TweetsAdapter(Context mContext, List<Tweet> mTweets , FragmentManager fm) {
        this.mContext = mContext;
        this.mTweets = mTweets;
        this.mFm = fm;
    }
    private Context getContext() {
        return mContext;
    }

    public void clear() {
        mTweets.clear();
        notifyDataSetChanged();
    }
    // Add a list of items
    public void addAll(List<Tweet> list) {
        mTweets.addAll(0,list);
        notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType){
            case 1:
                TweetRowLayoutImageBinding tweetImageBinding = DataBindingUtil
                        .inflate(inflater, R.layout.tweet_row_layout_image, parent, false);
                viewHolder = new TweetViewHolderWithImage(getContext(), tweetImageBinding, mFm);
                break;
            case 0:
                TweetRowLayoutBinding tweetBinding = DataBindingUtil
                        .inflate(inflater, R.layout.tweet_row_layout, parent, false);
                viewHolder = new TweetViewHolder(getContext(), tweetBinding, mFm);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        Tweet tweet = mTweets.get(position);
        switch (viewHolder.getItemViewType()){
            case 1:
                TweetViewHolderWithImage vhi = (TweetViewHolderWithImage) viewHolder;
                if (tweet != null) {
                    vhi.bindTweet(tweet);
                }
                break;
            case 0:
                TweetViewHolder vh = (TweetViewHolder) viewHolder;
                if (tweet != null) {
                    vh.bindTweet(tweet);
                }
                break;

        }

    }

    @Override
    public int getItemCount() {
        return mTweets.size();
    }

    @Override
    public int getItemViewType(int position) {
        String media = mTweets.get(position).getMedia_url();
        if (media != null && media.length() > 0) {
            return 1;
        } else  {
            return 0;
        }

    }
}
