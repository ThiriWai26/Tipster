package com.chann.tipster.data;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("rank")
    public int rank;

    @SerializedName("image")
    public String image;

    @SerializedName("winning_coins")
    public int coins;

    @SerializedName("fb_profile")
    public String fbProfile;
}
