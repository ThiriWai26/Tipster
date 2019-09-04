package com.chann.tipster.data;

import com.google.gson.annotations.SerializedName;

public class Profile {

    public boolean isSuccess;
    public String name;
    public String image;
    public long coin;

    @SerializedName("phone_number")
    public String phone;

    @SerializedName("tip_star_rank")
    public String tipStarRank;

}
