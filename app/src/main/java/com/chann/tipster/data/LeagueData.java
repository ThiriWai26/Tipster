package com.chann.tipster.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LeagueData {

    public int id;

    @SerializedName("fixture_id")
    public int fixtureId;

    @SerializedName("league")
    public League league;

    @SerializedName("local_team")
    public LocalTeam localTeam;

    @SerializedName("visitor_team")
    public VisitorTeam visitorTeam;

    @SerializedName("scores")
    public Score score;

    public String date;

    public String time;

    public String status;

    @SerializedName("prematch_odds")
    public List<PrematchOdds> prematchOddsList;
}



