package com.codepath.apps.mysimpletweets.dialogs;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TwitterApp;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.databinding.ComposeFragmentBinding;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnTweetListener} interface
 * to handle interaction events.
 * Use the {@link ComposeDialog#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ComposeDialog extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    ComposeFragmentBinding binding;
    EditText messageBox;
    TwitterClient twitterClient;
    Button btnTweet;
    ImageView cancelTweet;
    private OnTweetListener mListener;
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
    public interface OnTweetListener {
        // TODO: Update argument type and name
        void onSucessfulTweet();
    }

    public void setmListener(OnTweetListener mListener) {
        this.mListener = mListener;
    }



    public ComposeDialog() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ComposeDialog newInstance() {
        ComposeDialog fragment = new ComposeDialog();
        return fragment;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        messageBox = binding.etTweet;
        btnTweet = binding.btnTweet;
        cancelTweet = binding.ivCancel;
        twitterClient = TwitterApp.getRestClient();
        twitterClient.verifyCreds(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    Picasso.with(getContext()).load(response.getString("profile_image_url"))
                            .transform(new RoundedCornersTransformation(2,2)).into(binding.ivProfilePic);
                    String name = response.getString("name");
                    String screenName = response.getString("screen_name");
                    binding.tvName.setText(name);
                    binding.tvHashtag.setText("@"+screenName);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
        messageBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Fires right as the text is being changed (even supplies the range of text)
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // Fires right before text is changing
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Fires right after the text has changed
                int remaining_word_count = 140 - s.toString().length();
                if (remaining_word_count < 0) {
                    binding.tvWordCount.setTextColor(ContextCompat.getColor(getContext(),R.color.opaque_red));
                } else {
                    binding.tvWordCount.setTextColor(ContextCompat.getColor(getContext(),R.color.black));
                }
                binding.tvWordCount.setText(""+remaining_word_count);
            }
        });
        twitterClient = TwitterApp.getRestClient();
        btnTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tweetMessage = messageBox.getText().toString();
                twitterClient.postOnHomeTimeline(new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        mListener.onSucessfulTweet();
                        dismiss();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        Log.d("DEBUG", errorResponse.toString());
                    }
                },tweetMessage,null,null);
            }
        });
        cancelTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
dismiss();            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.compose_fragment, container, false);
        View view = binding.getRoot();
//here data must be an instance of the class MarsDataProvider
        return view;
    }


}
