package com.chann.tipster.data;


import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.google.gson.annotations.SerializedName;

public class OddsData extends BaseObservable {
    @SerializedName("isSuccess")
    public boolean isSuccess;

    @SerializedName("is_live_odds")
    public boolean isLive;

    @SerializedName("point")
    public Point point;

    @Bindable
    @SerializedName("bet_done")
    public BetDone betDone;

}
