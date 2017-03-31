package com.bvrith.snapdirect.gson;

/**
 * Created by Alex on 2014-11-27.
 */
public class UserProfile {
    public String name = "";
    public String phone = "";
    public String email = "";
    public String avatar = "";
    public String id = "";
    public String pushid = "";
    public Integer status;

    public void setNullFields(){
        if (name.isEmpty()) name = null;
        if (phone.isEmpty()) phone = null;
        if (email.isEmpty()) email = null;
        if (avatar.isEmpty()) avatar = null;
        if (id.isEmpty()) id = null;
        if (pushid.isEmpty()) pushid = null;
    }
}
