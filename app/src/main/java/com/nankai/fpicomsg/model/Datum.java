package com.nankai.fpicomsg.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Nankai on 9/10/2016.
 */
public class Datum implements Serializable{
    @SerializedName("title")
    public String title;
    @SerializedName("post_id")
    public int postId;
    @SerializedName("feature_image")
    public String featureImage;
    @SerializedName("count_images")
    public int countImages;
    @SerializedName("term_name")
    public String termName;
}
