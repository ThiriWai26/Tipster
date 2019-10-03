package com.chann.tipster.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MatchData {

    @SerializedName("id")
    public int id;

    @SerializedName("fixture_id")
    public int fixtureId;

    @SerializedName("league_id")
    public int leagyeId;

    @SerializedName("league_name")
    public String leagueName;

    @SerializedName("local_team_id")
    public int localTeamId;

    @SerializedName("local_team_name")
    public String localTeamName;

    @SerializedName("local_team_logo")
    public String localTeamLogo;

    @SerializedName("visitor_team_id")
    public int visitorTeamId;

    @SerializedName("visitor_team_name")
    public String visitorTeamName;

    @SerializedName("visitor_team_logo")
    public String visitorTeamLogo;

    @SerializedName("date")
    public String date;

    @SerializedName("time")
    public String time;

    @SerializedName("local_team_score")
    public int localTeamScore;

    @SerializedName("visitor_team_score")
    public int visitorTeamScore;

    @SerializedName("status")
    public String status;

    @SerializedName("created_at")
    public String createdAt;

    @SerializedName("updated_at")
    public String updtedAt;

    @SerializedName("handicap")
    public Handicap handiCap;

    @SerializedName("over_under")
    public OverUnder overUnder;

}
