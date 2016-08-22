package com.codepath.apps.mysimpletweets.view;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TwitterApp;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.activity.ReplyFragment;
import com.codepath.apps.mysimpletweets.databinding.TweetRowLayoutBinding;
import com.codepath.apps.mysimpletweets.databinding.TweetRowLayoutImageBinding;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.codepath.apps.mysimpletweets.utils.ParseRelativeDate;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by sdass on 8/21/16.
 */
public class TweetViewHolderWithImage extends RecyclerView.ViewHolder{
    TweetRowLayoutImageBinding binding;
    TwitterClient twitterClient;
    private Context mContext;
    FragmentManager mFm;
    Tweet tweet;
    ReplyFragment replyFragment;
    public TweetViewHolderWithImage(Context context, TweetRowLayoutImageBinding binding, FragmentManager fm) {
        super(binding.getRoot());
        this.binding = binding;
        this.mContext = context;
        this.mFm = fm;

    }
    public void bindTweet(final Tweet tweet) {
        this.tweet = tweet;
        binding.tvDetails.setText(tweet.getBody());
        binding.ivThumbnail.setImageResource(android.R.color.transparent);
        Picasso.with(mContext).load(tweet.getUser().getProfileImageURL()).
                transform(new RoundedCornersTransformation(5, 5)).into(binding.ivThumbnail);
        binding.tvHeadline.setText(tweet.getUser().getName());
        String relative_time = ParseRelativeDate.getRelativeTimeAgo(tweet.getCreatedAt());

        binding.tvTime.setText(relative_time);
        Picasso.with(mContext).load(R.drawable.reply_action).into(binding.ivReply);
        Picasso.with(mContext).load(R.drawable.retweet_action).into(binding.ivRetweet);
        binding.ivStar.setImageResource(0);

        if (Integer.parseInt(tweet.getFavorites_count()) == 0) {
            Picasso.with(mContext).load(R.drawable.star_icon).into(binding.ivStar);
        } else {
            binding.ivStar.setImageResource(R.drawable.fav_icon);
        }
        binding.tvNumOfLikes.setText(tweet.getFavorites_count());
        binding.tvNumOfRetweets.setText(tweet.getRetweet_count());

        binding.ivRetweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int new_retweet_count = Integer.parseInt(tweet.getRetweet_count()) + 1;
                tweet.setRetweet_count("" + new_retweet_count);
                binding.tvNumOfRetweets.setText("" + new_retweet_count);
                twitterClient = TwitterApp.getRestClient();
                twitterClient.retweet(new JsonHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                    }


                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                    }
                }, tweet.getUid());

            }
        });
        binding.ivStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.parseInt(tweet.getFavorites_count()) >= 0) {
                    binding.ivStar.setImageResource(0);
                    binding.ivStar.setImageResource(R.drawable.fav_icon);
                }
                int new_likes_count = Integer.parseInt(tweet.getFavorites_count()) + 1;
                tweet.setFavorites_count("" + new_likes_count);
                binding.tvNumOfLikes.setText("" + new_likes_count);
                twitterClient = TwitterApp.getRestClient();
                twitterClient.createFavorite(new JsonHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                    }


                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                    }
                }, tweet.getUid());
            }
        });
        Picasso.with(mContext).load(tweet.getMedia_url()).transform(new RoundedCornersTransformation(5, 5)).into(binding.ivRowMainImage);

        binding.ivReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showReplyFragment();
            }
        });

    }
    private void showReplyFragment() {

        replyFragment = ReplyFragment.newInstance(tweet);
        FragmentTransaction ft = mFm.beginTransaction();
        ft.add(replyFragment,"Reply").commit();
    }


}
