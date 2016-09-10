package com.nankai.fpicomsg.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Nankai on 9/10/2016.
 */
public class DatumDetail implements Serializable {
    @SerializedName("title")
    public String title;
    @SerializedName("src")
    public List<String> src;
}
