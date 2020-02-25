package com.mounts.ballkan.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AdsResponse {

    @SerializedName("isSuccess")
    public boolean isSuccess;

    @SerializedName("ads")
    public List<Ads> ads;
}
