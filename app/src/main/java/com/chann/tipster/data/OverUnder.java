package com.chann.tipster.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OverUnder {

    @SerializedName("id")
    public int id;

    @SerializedName("value")
    public int value;

    @SerializedName("total_score")
    public int totalScore;

    @SerializedName("last_update")
    public String lastUpdate;

    @SerializedName("match_id")
    public int matchId;

}
