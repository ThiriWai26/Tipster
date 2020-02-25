package com.mounts.ballkan.data;

import com.google.gson.annotations.SerializedName;

public class Data {

    public String label;
    public int handicap;
    public double tax;
    public int value;

    @SerializedName("team_type") //not for over_under
    public int teamType;

    @SerializedName("bet_done")
    public boolean isBet;
}
