package com.nankai.fpicomsg.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nankai on 9/10/2016.
 */
public class Gallery implements Serializable{
    @SerializedName("status")
    public boolean status;
    @SerializedName("code")
    public int code;
    @SerializedName("message")
    public String message;
    @SerializedName("data")
    public List<Datum> data = new ArrayList<Datum>();
}
