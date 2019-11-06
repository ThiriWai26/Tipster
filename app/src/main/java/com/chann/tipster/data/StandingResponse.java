package com.chann.tipster.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StandingResponse {

    @SerializedName("isSuccess")
    public boolean isSuccess;

    @SerializedName("user")
    public User user;

    @SerializedName("data")
    public List<UserStanding> userStandings;
}
