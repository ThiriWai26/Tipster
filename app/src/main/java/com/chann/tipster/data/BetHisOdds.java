package com.chann.tipster.data;


import com.google.gson.annotations.SerializedName;

public class BetHisOdds {

    @SerializedName("label")
    public String label ;

    @SerializedName("value")
    public int value;

    @SerializedName("handicap")
    public int handicap;

    @SerializedName("total_score")
    public int totalScore;

}
