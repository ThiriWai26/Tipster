package com.chann.tipster.data;

import com.google.gson.annotations.SerializedName;

public class OddsData {
    public boolean isSuccess;
    @SerializedName("is_live_odds")
    public boolean isLive;
    public int point;
    @SerializedName("odds")
    public Odds odds;
}
