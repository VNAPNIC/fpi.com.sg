package com.nankai.fpicomsg.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by namIT on 9/9/2016.
 */
public class ServiceModel implements Serializable {

    @SerializedName("id")
    public String id;
    @SerializedName("name")
    public String name;
    @SerializedName("image")
    public String image;
    @SerializedName("text")
    public String text;
}
