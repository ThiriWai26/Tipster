package com.chann.tipster.data;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.google.gson.annotations.SerializedName;
import com.squareup.picasso.Picasso;

public class BetHistoryData {

    @SerializedName("label")
    public int label;

    @SerializedName("coin")
    public int coin;

    @SerializedName("value")
    public int value;

    @SerializedName("handicap")
    public int handicap;

    @SerializedName("date")
    public String date;

    @SerializedName("time")
    public String time;

    @SerializedName("outcomes")
    public Integer outcomes;

    @SerializedName("winning")
    public Integer winning;

    @SerializedName("match_id")
    public int matchId;

    @SerializedName("local_name")
    public String localName;

    @SerializedName("visitor_name")
    public String visitorName;

    @SerializedName("local_score")
    public Integer localScore;

    @SerializedName("visitor_score")
    public Integer visitorScore;

    @SerializedName("local_logo")
    public String localLogo;

    @SerializedName("visitor_logo")
    public String visitorLogo;

    @SerializedName("total_score")
    public int totalScore;

    @SerializedName("bet_type")
    public int betType;

    @BindingAdapter("loadImage")
    public static void loadImage(ImageView view,String url){
        Picasso.get().load(url).resize(50, 50).into(view);
    }
}
