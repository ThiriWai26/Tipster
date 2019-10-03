package com.chann.tipster.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MatchListData {

    @SerializedName("league_name")
    public String leagueName;

    @SerializedName("data")
    public List<MatchData> matchData;
}
