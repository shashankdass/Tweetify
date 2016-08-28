package com.codepath.apps.mysimpletweets;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.FlickrApi;
import org.scribe.builder.api.TwitterApi;

import android.content.Context;

import com.codepath.apps.mysimpletweets.utils.NetworkHealthChecker;
import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
	public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
	public static final String REST_URL = "https://api.twitter.com/1.1"; // Change this, base API URL
	public static final String REST_CONSUMER_KEY = "YD5hZezplp4Me7SMsdAKX0Qud";       // Change this
	public static final String REST_CONSUMER_SECRET = "9EfkF6JkAOcYNDQ50FczGmAKna5tRc7nnvyex23iZf7N8M53ib"; // Change this
	public static final String REST_CALLBACK_URL = "oauth://cpsimpletweets"; // Change this (here and in manifest)

	public TwitterClient(Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
	}


	/* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
	 * 	  i.e getApiUrl("statuses/home_timeline.json");
	 * 2. Define the parameters to pass to the request (query or body)
	 *    i.e RequestParams params = new RequestParams("foo", "bar");
	 * 3. Define the request method and make a call to the client
	 *    i.e client.get(apiUrl, params, handler);
	 *    i.e client.post(apiUrl, params, handler);
	 */
	public void getHomeTimeline(AsyncHttpResponseHandler handler, long max_id, long since_id) throws NetworkFailure {
		String apiUrl = getApiUrl("statuses/home_timeline.json");
		RequestParams params = new RequestParams();

		params.put("count", 25);
		params.put("since_id",since_id);
		if(max_id > 0)
			params.put("max_id", max_id);
		if (new NetworkHealthChecker(context).checkNetworkHealth())
			getClient().get(apiUrl, params, handler);
		else {
			throw new NetworkFailure();
		}
	}

	public void getMentionsTimeline(AsyncHttpResponseHandler handler, long max_id, long since_id) throws NetworkFailure {
		String apiUrl = getApiUrl("statuses/mentions_timeline.json");
		RequestParams params = new RequestParams();

		params.put("count", 25);
		if (new NetworkHealthChecker(context).checkNetworkHealth())
			getClient().get(apiUrl, params, handler);
		else {
			throw new NetworkFailure();
		}
	}

	public void postOnHomeTimeline(AsyncHttpResponseHandler handler, String statusText, String replyStatusId, String userScreenName){
		String apiUrl = getApiUrl("statuses/update.json");
		RequestParams params = new RequestParams();
		if(userScreenName != null && !userScreenName.equals(""))
			params.put("status","@"+userScreenName+" "+statusText);
		else {
			params.put("status",statusText);

		}
		if(replyStatusId!= null && !replyStatusId.equals(""))
				params.put("in_reply_to_status_id",replyStatusId);
		getClient().post(apiUrl,params,handler);

	}

	public void retweet(AsyncHttpResponseHandler handler, long tweet_id){
		String apiUrl = getApiUrl("statuses/retweet/"+tweet_id+".json");
			getClient().post(apiUrl,handler);


	}

	public void createFavorite(AsyncHttpResponseHandler handler, long tweet_id){
		String apiUrl = getApiUrl("favorites/create.json?id="+tweet_id);
		getClient().post(apiUrl,handler);
	}

	public void getUserInfo( AsyncHttpResponseHandler handler) throws NetworkFailure {
		//https://api.twitter.com/1.1/account/verify_credentials.json
		String apiUrl = getApiUrl("account/verify_credentials.json");

		if (new NetworkHealthChecker(context).checkNetworkHealth())
			getClient().get(apiUrl, null, handler);
		else {
			throw new NetworkFailure();
		}
	}

	public void getUserTimeline(String screenName, AsyncHttpResponseHandler handler) throws NetworkFailure {
		String apiUrl = getApiUrl("statuses/user_timeline.json");
		RequestParams params = new RequestParams();
		params.put("screen_name",screenName);

		params.put("count", 25);
		if (new NetworkHealthChecker(context).checkNetworkHealth())
			getClient().get(apiUrl, params, handler);
		else {
			throw new NetworkFailure();
		}

	}

	public void getSearchResults(String query, AsyncHttpResponseHandler handler) throws NetworkFailure {
		String apiUrl = getApiUrl("search/tweets.json");
		RequestParams params = new RequestParams();
		params.put("q",query);
		params.put("result_type","mixed");
		params.put("count", 25);
		if (new NetworkHealthChecker(context).checkNetworkHealth())
			getClient().get(apiUrl, params, handler);
		else {
			throw new NetworkFailure();
		}

	}

	public void getFollowersList(String screenName, AsyncHttpResponseHandler handler) throws NetworkFailure {
		String apiUrl = getApiUrl("followers/list.json");
		RequestParams params = new RequestParams();
		params.put("screen_name",screenName);
		params.put("skip_status","true");
		params.put("count", 25);
		params.put("include_user_entities","false");
		if (new NetworkHealthChecker(context).checkNetworkHealth())
			getClient().get(apiUrl, params, handler);
		else {
			throw new NetworkFailure();
		}
	}

	public void getFollowingList(String screenName, AsyncHttpResponseHandler handler) throws NetworkFailure {
		String apiUrl = getApiUrl("friends/list.json");
		RequestParams params = new RequestParams();
		params.put("screen_name",screenName);
		params.put("skip_status","true");
		params.put("count", 25);
		params.put("include_user_entities","false");
		if (new NetworkHealthChecker(context).checkNetworkHealth())
			getClient().get(apiUrl, params, handler);
		else {
			throw new NetworkFailure();
		}
	}

}