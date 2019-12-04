package com.chann.tipster.data;

import com.google.gson.annotations.SerializedName;

public class OverUnderOdds {

    @SerializedName("total_score")
    public Integer totalScore;

    @SerializedName("value")
    public Integer value;

    @SerializedName("label")
    public String label;
}
