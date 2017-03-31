package com.bvrith.snapdirect.network;

import com.bvrith.snapdirect.gson.UserProfile;

import java.util.Map;

/**
 * @author Andrei Alikov andrei.alikov@gmail.com
 */
public interface ServerResponseHandler {
    void onFollowersResponse(Map<String, UserProfile> users);
}
