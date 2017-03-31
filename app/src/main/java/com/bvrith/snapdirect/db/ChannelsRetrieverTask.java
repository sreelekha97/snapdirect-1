package com.bvrith.snapdirect.db;

/**
 * Created by aleksey.boev on 2015-04-16.
 */

import android.os.AsyncTask;

import com.android.volley.Response;
import com.bvrith.snapdirect.classes.CallbackInterface;
import com.bvrith.snapdirect.common.Snapdirect;
import com.bvrith.snapdirect.gson.UserProfile;
import com.bvrith.snapdirect.network.ServerInterface;

import java.util.ArrayList;

/**
 * Created by maximilian on 11/29/14.
 */

public class ChannelsRetrieverTask extends AsyncTask<String, Void, String> {
    private static String LOG_TAG = "ChannelsRetrieverTask";

    private CallbackInterface callbackInterface;

    public ChannelsRetrieverTask(CallbackInterface callbackInterface)
    {
        this.callbackInterface = callbackInterface;
    }

    @Override
    protected String doInBackground(String... params) {
        ServerInterface.getChannelsRequest(Snapdirect.getMainActivity(),
                new Response.Listener<ArrayList<UserProfile>>() {
                    @Override
                    public void onResponse(ArrayList<UserProfile> response) {
                        Boolean boolNewItems = false;
                        for (int i = 0; i < response.size(); i++) {
                            if (Snapdirect.getFriendsDataSource().
                                    getChannelByChannelId(response.get(i).id)==null) {
                                Snapdirect.getFriendsDataSource().createChannel(
                                        response.get(i).name, "", response.get(i).id,
                                        response.get(i).avatar, FriendEntry.INT_STATUS_DEFAULT);
                                boolNewItems = true;
                            } else {
                                FriendEntry channel = Snapdirect.getFriendsDataSource().
                                        getChannelByChannelId(response.get(i).id);
                                channel.setAvatar(response.get(i).avatar);
                                channel.setName(response.get(i).name);
                                Snapdirect.getFriendsDataSource().updateFriend(channel);
                            }
                        }
                        if (boolNewItems && callbackInterface != null)
                            callbackInterface.onResponse(
                                    Snapdirect.getFriendsDataSource().getAllChannels());
                    }
                }, null);
        ArrayList<FriendEntry> channels = Snapdirect.getFriendsDataSource().getAllChannels();
        if (callbackInterface != null)
            callbackInterface.onResponse(channels);
        return null;
    }
}
