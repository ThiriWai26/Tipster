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

    @SerializedName("bet_type")
    public int betType;

    @SerializedName("match")
    public BetHisMatch match;

    @SerializedName("odds")
    public BetHisOdds odds;

    @BindingAdapter("loadImage")
    public static void getImage(ImageView view, String url){
        Picasso.get().load(url).resize(50, 50).into(view);
    }
}
