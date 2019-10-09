package com.chann.tipster.data;

import com.google.gson.annotations.SerializedName;

public class BetHistoryData {

    @SerializedName("label")
    public int label;

    @SerializedName("coin")
    public int coin;

    @SerializedName("value")
    public int value;

    @SerializedName("handicap")
    public int handicap;

    @SerializedName("date")
    public String date;

    @SerializedName("time")
    public String time;

    @SerializedName("outcomes")
    public String outcomes;

    @SerializedName("winning")
    public String winning;

    @SerializedName("match_id")
    public int matchId;

    @SerializedName("local_name")
    public String localName;

    @SerializedName("visitor_name")
    public String visitorName;

    @SerializedName("local_score")
    public int localScore;

    @SerializedName("visitor_score")
    public int visitorScore;

    @SerializedName("local_logo")
    public String localLogo;

    @SerializedName("visitor_logo")
    public String visitorLogo;

    @SerializedName("total_score")
    public int totalScore;

    @SerializedName("bet_type")
    public int betType;

}
