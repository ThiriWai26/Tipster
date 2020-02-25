package com.mounts.ballkan.data;

import com.google.gson.annotations.SerializedName;

public class Profile {

    public boolean isSuccess;
    public String name;
    public String image;

    @SerializedName("fb_profile")
    public String fbProfile;

    @SerializedName("phone_number")
    public String phone;

    @SerializedName("tip_star_rank")
    public String tipStarRank;

}
