package com.chann.tipster.data;

import com.google.gson.annotations.SerializedName;

public class Ads {
    @SerializedName("id")
    public int id;

    @SerializedName("image")
    public String image;

    @SerializedName("link")
    public String link;
}
