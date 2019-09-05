package com.chann.tipster.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Handicap {

    @SerializedName("last_update")
    public LastUpdate lastUpdate;

    @SerializedName("data")
    public List<Data> data;


}
