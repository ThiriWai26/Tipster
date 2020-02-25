package com.mounts.ballkan.data;

import com.google.gson.annotations.SerializedName;

public class Odds {

    @SerializedName("handicap")
    public Handicap handicap;

    @SerializedName("Over_Under")
    public OverUnder overUnder;


}
