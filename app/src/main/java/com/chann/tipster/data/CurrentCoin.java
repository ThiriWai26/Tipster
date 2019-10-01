package com.chann.tipster.data;

import com.google.gson.annotations.SerializedName;

public class CurrentCoin {

    public boolean isSuccess;

    @SerializedName("current_coin")
    public long currentCoin;
}
