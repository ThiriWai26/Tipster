package com.mounts.ballkan.data;

import com.google.gson.annotations.SerializedName;

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
