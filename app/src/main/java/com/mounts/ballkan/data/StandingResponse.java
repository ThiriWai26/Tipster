package com.mounts.ballkan.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StandingResponse {

    @SerializedName("isSuccess")
    public boolean isSuccess;

    @SerializedName("user")
    public User user;

    @SerializedName("next_page_url")
    public String nextPage;

    @SerializedName("data")
    public List<UserStanding> userStandings;
}
