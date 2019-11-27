package com.chann.tipster.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MatchListResponse {

    @SerializedName("isSuccess")
    public boolean isSuccess;

    @SerializedName("room")
    public Room room;

    @SerializedName("data")
    public List<MatchListData>  matchListData;

    @SerializedName("date")
    public MatchDates matchDates;
}
