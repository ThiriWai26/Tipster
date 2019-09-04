package com.chann.tipster.data;

import com.google.gson.annotations.SerializedName;

class PrematchOdds {
    public int label;
    public int handicap;
    public int value;

    @SerializedName("team_type")
    public int teamType;

}
