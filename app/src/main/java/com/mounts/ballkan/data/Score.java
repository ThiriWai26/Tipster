package com.mounts.ballkan.data;

import com.google.gson.annotations.SerializedName;

public class Score {

    @SerializedName("local_team_score")
    public int localScore;

    @SerializedName("visitor_team_score")
    public int visitorScore;
}
