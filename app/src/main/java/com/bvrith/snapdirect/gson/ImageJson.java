package com.bvrith.snapdirect.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Alex on 2014-12-10.
 */
public class ImageJson {
    public String image_id = "";
    public long timestamp = 0;
    public String title = "";
    public String url_original = "";
    public String url_medium = "";
    public String url_small = "";
    public String local_uri = "";
    public Integer status = 0;
    public String[] likes = new String[]{};
    @SerializedName("aspect_ratio")
    public double ratio = 1.0;
}

