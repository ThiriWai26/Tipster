package com.chann.tipster.data;

import com.google.gson.annotations.SerializedName;

public class HandiBetInfo {

    @SerializedName("label")
    public String label;

    @SerializedName("coin")
    public Integer coin;

    @SerializedName("date")
    public String date;

    @SerializedName("time")
    public String time;

    @SerializedName("outcomes")
    public Integer outcomes;

    @SerializedName("winning")
    public Integer winning;


}
