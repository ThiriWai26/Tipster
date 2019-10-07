package com.chann.tipster.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MatchListResponse {

    @SerializedName("isSuccess")
    public boolean isSuccess;

    @SerializedName("room_id")
    public int roomId;

    @SerializedName("current_date")
    public String currentDate;

    @SerializedName("data")
    public List<MatchListData>  matchListData;

}
