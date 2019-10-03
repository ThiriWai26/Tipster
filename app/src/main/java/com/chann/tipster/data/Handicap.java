package com.chann.tipster.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Handicap {

    @SerializedName("id")
    public int id;

    @SerializedName("label")
    public String label;

    @SerializedName("value")
    public int value;

    @SerializedName("handicap")
    public int handicap;

    @SerializedName("last_update")
    public String lastUpdate;

    @SerializedName("match_id")
    public int matchId;

}
