package com.nankai.fpicomsg.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Nankai on 9/10/2016.
 */
public class GralleryDetail implements Serializable {
    @SerializedName("status")
    public boolean status;
    @SerializedName("message")
    public String message;
    @SerializedName("data")
    public DatumDetail data;
}
