package com.chann.tipster.data;

import com.google.gson.annotations.SerializedName;

public class UserStanding {

    @SerializedName("user_id")
    public int userId;

    @SerializedName("coin")
    public int coin;

    @SerializedName("rank")
    public int rank;

    @SerializedName("image")
    public String image;

    @SerializedName("name")
    public String name;

}
