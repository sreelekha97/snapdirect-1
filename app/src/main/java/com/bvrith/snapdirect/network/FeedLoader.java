package com.bvrith.snapdirect.network;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bvrith.snapdirect.FeedListView;
import com.bvrith.snapdirect.common.Constants;
import com.bvrith.snapdirect.common.Snapdirect;
import com.bvrith.snapdirect.gson.FeedEntryJson;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Alex on 2014-12-10.
 */
public class FeedLoader {
    private static String LOG_TAG = "FeedLoader";
    //private ArrayList<FeedEntryJson> mFeedList;
    //private FeedAdapter mAdapter;
    private FeedListView mFeedListView;
    private Integer mPageSize = 100;
    private Integer mOffset = 0;
    private Boolean isLoading = false;

    public void setFeedListView(FeedListView feedListView) {
        this.mFeedListView = feedListView;
    }

    public void loadFeed() {
        HashMap<String, String> headers = new HashMap<String,String>();
        headers.put(Constants.HEADER_USERID, Snapdirect.getPreferences().strUserID);
        headers.put("Accept", "*/*");
        Log.d(LOG_TAG, "Load feed request with offset = " + mOffset +
                " and limit " + mPageSize);
        StringRequest getFeedRequest = new StringRequest(Request.Method.GET,
                Constants.SERVER_URL+Constants.SERVER_PATH_FEED+
                "?offset=" + mOffset + "&limit=" + mPageSize,
                "", headers,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(LOG_TAG, response.toString());
                        try {
                            Log.d(LOG_TAG, "Response " + response);
                            Gson gson = new Gson();
                            JSONObject resJson = new JSONObject(response);
                            String strRes = resJson.getString(Constants.RESPONSE_RESULT);
                            if ((strRes.equals(Constants.RESPONSE_RESULT_OK)) && 
                                    (resJson.has(Constants.RESPONSE_DATA))) {
                                Type type = new TypeToken<ArrayList<FeedEntryJson>>(){}.getType();
                                ArrayList<FeedEntryJson> feedList = gson.fromJson(
                                        resJson.get(Constants.RESPONSE_DATA).toString(), type);
                                if (mOffset == 0) {
                                    mFeedListView.addNewData(feedList, true);
                                    //mFeedList.clear();
                                    //mFeedList.addAll(feedList);
                                    //mAdapter.add();
                                } else {
                                    mFeedListView.addNewData(feedList, false);
                                    //mFeedList.addAll(feedList);
                                    //mAdapter.notifyDataSetChanged();
                                }
                                mOffset = mFeedListView.mFeedList.size();
                                isLoading = false;
                                Log.d(LOG_TAG, "Loaded  " + feedList.size()
                                        + " feed items");
                                //mAdapter.notifyDataSetChanged();
                            }
                        } catch (Exception e) {
                            Log.d(LOG_TAG, "Exception " + e.getLocalizedMessage());
                            handleFailure();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                 if ((error != null) && (error.networkResponse != null)
                         && (error.networkResponse.data != null))
                    Log.d(LOG_TAG, "Error: " +
                            new String(error.networkResponse.data));
                    handleFailure();
                }
            }
        );
        if (!isLoading) VolleySingleton.getInstance(Snapdirect.getMainActivity()).
                addToRequestQueue(getFeedRequest);
    }

    public void handleFailure(){mFeedListView.addNewData(null, false);isLoading = false;}
}
