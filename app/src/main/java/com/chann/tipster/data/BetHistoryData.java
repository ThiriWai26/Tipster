package com.chann.tipster.data;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.google.gson.annotations.SerializedName;
import com.squareup.picasso.Picasso;

public class BetHistoryData {

    @SerializedName("bet_match_id")
    public int betMatchid;

    @SerializedName("handicap_bet_info")
    public HandiBetInfo handiBetInfo;

    @SerializedName("handicap_odds")
    public HandicapOdds handicapOdds;

    @SerializedName("over_under_bet_info")
    public OverUnderBetInfo overUnderBetInfo;

    @SerializedName("over_under_odds")
    public OverUnderOdds overUnderOdds;

    @SerializedName("match")
    public BetHisMatch match;

    @BindingAdapter("loadImage")
    public static void getImage(ImageView view, String url){
        Picasso.get().load(url).resize(50, 50).into(view);
    }

}
