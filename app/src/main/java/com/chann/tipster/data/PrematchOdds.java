package com.chann.tipster.data;

import com.google.gson.annotations.SerializedName;

public class PrematchOdds {
    public int label;
    public int handicap;
    public int value;

    @SerializedName("team_type")
    public int teamType;

}
