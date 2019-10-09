package com.chann.tipster.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StandingResponse {

    @SerializedName("isSuccess")
    public boolean isSuccess;

    @SerializedName("rank")
    public int rank;

    @SerializedName("image")
    public String image;

    @SerializedName("winning_coins")
    public int coins;

    @SerializedName("data")
    public List<UserStanding> userStandings;
}
