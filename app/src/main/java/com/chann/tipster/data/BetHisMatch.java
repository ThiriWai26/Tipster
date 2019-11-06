package com.chann.tipster.data;

import com.google.gson.annotations.SerializedName;

public class BetHisMatch {

    @SerializedName("local_team_name")
    public String localName;

    @SerializedName("visitor_team_name")
    public String visitorName;

    @SerializedName("local_team_score")
    public Integer localScore;

    @SerializedName("visitor_team_score")
    public Integer visitorScore;

    @SerializedName("local_team_logo")
    public String localLogo;

    @SerializedName("visitor_team_logo")
    public String visitorLogo;

}
