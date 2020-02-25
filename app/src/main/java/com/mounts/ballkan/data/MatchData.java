package com.mounts.ballkan.data;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import com.google.gson.annotations.SerializedName;
import com.mounts.ballkan.R;
import com.squareup.picasso.Picasso;

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
    public Integer localTeamScore;

    @SerializedName("visitor_team_score")
    public Integer visitorTeamScore;

    @SerializedName("status")
    public String status;

    @SerializedName("created_at")
    public String createdAt;

    @SerializedName("updated_at")
    public String updtedAt;

    @SerializedName("tmp_handicap")
    public Handicap handiCap;

    @SerializedName("tmp_over_under")
    public OverUnder overUnder;

    @BindingAdapter("getImage")
    public static void loadImage(ImageView view , String url){
        Picasso.get().load(url).resize(50,50)
                .placeholder(R.drawable.logo_tipstar).into(view);
    }

}
