package com.mounts.ballkan.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;


import com.mounts.ballkan.R;
import com.mounts.ballkan.data.AdsResponse;
import com.mounts.ballkan.data.BetResponse;
import com.mounts.ballkan.data.MatchData;
import com.mounts.ballkan.data.OddsData;
import com.mounts.ballkan.data.Token;
import com.mounts.ballkan.databinding.ActivityOddsBinding;
import com.mounts.ballkan.retrofit.RetrofitService;
import com.squareup.picasso.Picasso;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
public class OddsActivity extends AppCompatActivity {

    private static MatchData league;
    private OddsData oddsData;
    private static int room_id;
    private int betAmount = 0;
    private CompositeDisposable disposable;
    private ActivityOddsBinding binding;
    private String handiOdds;

    private int typeId = 1, handiOddsId, overOddsId, handcap, value;
    private int random;
    private boolean isLiveOdds;
    private String imgLink = null;

    public static Intent getInstance(Context context, MatchData matchData, int roomId) {
        league = matchData;
        room_id = roomId;
        Intent intent = new Intent(context, OddsActivity.class);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_odds);
        binding = DataBindingUtil.setContentView(this , R.layout.activity_odds);
        disposable = new CompositeDisposable();
        bindData();
        getOdd();
        getAds();
    }

    private void getAds() {

        Disposable subscribe = RetrofitService.getApiEnd().getAds(Token.token)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleAdsResult);
        disposable.add(subscribe);

    }

    private void handleAdsResult(AdsResponse adsResponse) {
        if(adsResponse.ads.size()!= 0 ){
            random = (int) (Math.random()*adsResponse.ads.size());
            Picasso.get().load(RetrofitService.BASE_URL+"/api/get_image/"+adsResponse.ads.get(random).image)
                    .resize(100,100)
                    .into(binding.imgAds);
            imgLink = adsResponse.ads.get(random).link;

            Log.e("random",String.valueOf(random));

        }
        else {
            binding.imgAds.setVisibility(View.GONE);
            binding.imgClose.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }

    @SuppressLint("CheckResult")
    private void getOdd() {

        Disposable subscribe = RetrofitService.getApiEnd().getOddsData(Token.token, league.id, typeId, room_id)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResult, this::handleError);

        disposable.add(subscribe);

    }

    private void handleError(Throwable throwable) {
        Log.e("failure", throwable.toString());
    }


    private void handleResult(OddsData oddsData) {
        if (oddsData.isSuccess) {
            this.oddsData = oddsData;
            isLiveOdds = oddsData.isLive;
            binding.setOdds(oddsData);
            binding.setOddsActivity(this);

        } else {
          Toast.makeText(this, "The match is finished" , Toast.LENGTH_LONG ).show();

        }
    }



    @SuppressLint("SetTextI18n")
    private void bindData() {

        binding.setData(league);

        Log.e("local goal",String.valueOf(league.localTeamScore));
        Log.e("visitro goal",String.valueOf(league.visitorTeamScore));

        handiOddsId = league.handiCap.id;
        overOddsId = league.overUnder.id;

        handcap = league.overUnder.totalScore;
        value = league.overUnder.value;

        if (league.handiCap.handicap == 0) {
            if (league.handiCap.value < 0)
                handiOdds = "(L" + league.handiCap.value + ")";

            else
                handiOdds = "(L" + "+" + league.handiCap.value + ")";
        } else {
            if (league.handiCap.value <0)
                handiOdds = "(" + league.handiCap.handicap + league.handiCap.value + ")";

            else
                handiOdds = "(" + league.handiCap.handicap + "+" + league.handiCap.value + ")";

        }

        if (league.handiCap.label.equals("Home")) {

            binding.tvLocalValue.setText(handiOdds);


        } else {
            binding.tvVisitorValue.setText(handiOdds);
        }


        if (league.overUnder.value < 0) {

            @SuppressLint("DefaultLocale") String overValue = String.format("(%d%d)", league.overUnder.totalScore, league.overUnder.value);
            binding.tvOver.setText(overValue);
            binding.tvUnder.setText(overValue);

        } else {
            @SuppressLint("DefaultLocale") String overValue = String.format("(%d+%d)", league.overUnder.totalScore, league.overUnder.value);
            binding.tvOver.setText(overValue);
            binding.tvUnder.setText(overValue);
        }


    }


    public void onLocalTeamBetClick(View view , boolean isBet ) {

        if(isLiveOdds){
            Toast.makeText(this , "Live odds cannot be bet",Toast.LENGTH_LONG).show();
        }
        else {
            if(isBet){
                Toast.makeText(this , "This odds is already bet.",Toast.LENGTH_LONG).show();
            }

            else {
                if(oddsData.betDone.getVisitor()){
                    Toast.makeText(this , "Only one item can be bet.",Toast.LENGTH_LONG).show();
                }
                else {

                    final Dialog dialog = new Dialog(this);
                    final RadioButton minBtn, maxBtn;
                    ImageView imgTeamLogo;
                    TextView tvCancel, tvBet, labelOverUnder;

                    dialog.setContentView(R.layout.dialog_bet);
                    imgTeamLogo = dialog.findViewById(R.id.imgTeamLogo);
                    tvCancel = dialog.findViewById(R.id.tvCancel);
                    tvBet = dialog.findViewById(R.id.tvBet);
                    minBtn = dialog.findViewById(R.id.minAmount);
                    maxBtn = dialog.findViewById(R.id.maxAmount);
                    labelOverUnder = dialog.findViewById(R.id.labelOverUnder);
                    labelOverUnder.setVisibility(View.GONE);

                    Picasso.get().load(league.localTeamLogo).resize(50, 50).into(imgTeamLogo);
                    minBtn.setText(String.valueOf(oddsData.point.min));
                    maxBtn.setText(String.valueOf(oddsData.point.max));


                    tvCancel.setOnClickListener(view1 -> dialog.dismiss());
                    tvBet.setOnClickListener(view12 -> {

                        Log.e("bet", "success");

                        if (minBtn.isChecked()) {
                            betAmount = Integer.parseInt(minBtn.getText().toString());
                            Log.e("min_bet_amount", String.valueOf(betAmount));
                        } else if (maxBtn.isChecked()) {
                            betAmount = Integer.parseInt(maxBtn.getText().toString());
                            Log.e("max_bet_amount", String.valueOf(betAmount));
                        }

                        onBet(handiOddsId,betAmount, 1, 1, dialog);

                    });

                    dialog.show();

                }
            }
        }


    }

    public void onVisitorTeamBetClick(View view , boolean isBet ) {


        if(isLiveOdds){
            Toast.makeText(this , "Live odds cannot be bet",Toast.LENGTH_LONG).show();
        }
        else {
            if(isBet){
                Toast.makeText(this , "This odds is already bet.",Toast.LENGTH_LONG).show();
            }
            else {
                if(oddsData.betDone.getLocal()){
                    Toast.makeText(this , "Only one item can be bet.",Toast.LENGTH_LONG).show();
                }
                else {


                    final Dialog dialog = new Dialog(this);
                    final RadioButton minBtn, maxBtn;
                    ImageView imgTeamLogo;
                    TextView tvCancel, tvBet, labelOverUnder;

                    dialog.setContentView(R.layout.dialog_bet);
                    imgTeamLogo = dialog.findViewById(R.id.imgTeamLogo);
                    tvCancel = dialog.findViewById(R.id.tvCancel);
                    tvBet = dialog.findViewById(R.id.tvBet);
                    minBtn = dialog.findViewById(R.id.minAmount);
                    maxBtn = dialog.findViewById(R.id.maxAmount);

                    Picasso.get().load(league.visitorTeamLogo).resize(50, 50).into(imgTeamLogo);
                    minBtn.setText(String.valueOf(oddsData.point.min));
                    maxBtn.setText(String.valueOf(oddsData.point.max));

                    labelOverUnder = dialog.findViewById(R.id.labelOverUnder);

                    labelOverUnder.setVisibility(View.GONE);

                    tvCancel.setOnClickListener(view1 -> dialog.dismiss());

                    tvBet.setOnClickListener(view12 -> {

                        if (minBtn.isChecked()) {
                            betAmount = Integer.parseInt(minBtn.getText().toString());
                        } else if (maxBtn.isChecked()) {
                            betAmount = Integer.parseInt(maxBtn.getText().toString());
                        }

                        onBet(handiOddsId,betAmount, 2, 1, dialog);
                        dialog.dismiss();
                    });
                    dialog.show();
                }
            }
        }

    }

    public void onOverBetClick(View view , boolean isBet) {


        if(isLiveOdds){
            Toast.makeText(this , "Live odds cannot be bet",Toast.LENGTH_LONG).show();
        }
        else {
            if(isBet){
                Toast.makeText(this , "This odds is already bet.",Toast.LENGTH_LONG).show();
            }
            else {
                if(oddsData.betDone.getUnder()){
                    Toast.makeText(this , "Only one item can be bet.",Toast.LENGTH_LONG).show();
                }

                else {

                    Log.e("This bet ","over");
                    final Dialog dialog = new Dialog(this);
                    final RadioButton minBtn, maxBtn;
                    TextView tvOverUnder, tvCancel, tvBet;
                    ImageView imgTeamLogo;

                    dialog.setContentView(R.layout.dialog_bet);
                    tvOverUnder = dialog.findViewById(R.id.labelOverUnder);
                    tvCancel = dialog.findViewById(R.id.tvCancel);
                    tvBet = dialog.findViewById(R.id.tvBet);
                    minBtn = dialog.findViewById(R.id.minAmount);
                    maxBtn = dialog.findViewById(R.id.maxAmount);
                    imgTeamLogo = dialog.findViewById(R.id.imgTeamLogo);

                    imgTeamLogo.setVisibility(View.GONE);

                    tvOverUnder.setText("Over");
                    minBtn.setText(String.valueOf(oddsData.point.min));
                    maxBtn.setText(String.valueOf(oddsData.point.max));

                    tvCancel.setOnClickListener(view1 -> dialog.dismiss());
                    tvBet.setOnClickListener(view12 -> {

                        if (minBtn.isChecked()) {
                            betAmount = Integer.parseInt(minBtn.getText().toString());
                        } else if (maxBtn.isChecked()) {
                            betAmount = Integer.parseInt(maxBtn.getText().toString());
                        }

                        onBet(overOddsId,betAmount, 1, 2, dialog);
                    });
                    dialog.show();
                }
            }
        }

    }

    public void onUnderBetClick(View view , boolean isBet) {


        if(isLiveOdds){
            Toast.makeText(this , "Live odds cannot be bet",Toast.LENGTH_LONG).show();
        }
        else {
            if(isBet){
                Toast.makeText(this , "This odds is already bet.",Toast.LENGTH_LONG).show();
            }
            else {
                if(oddsData.betDone.getOver()){
                    Toast.makeText(this , "Only one item can be bet.",Toast.LENGTH_LONG).show();
                }
                else {

                    Log.e("This bet ","over");
                    final Dialog dialog = new Dialog(this);
                    final RadioButton minBtn, maxBtn;
                    TextView tvOverUnder, tvCancel, tvBet;
                    ImageView imgTeamLogo;

                    dialog.setContentView(R.layout.dialog_bet);
                    tvCancel = dialog.findViewById(R.id.tvCancel);
                    tvBet = dialog.findViewById(R.id.tvBet);

                    tvOverUnder = dialog.findViewById(R.id.labelOverUnder);
                    minBtn = dialog.findViewById(R.id.minAmount);
                    maxBtn = dialog.findViewById(R.id.maxAmount);
                    imgTeamLogo = dialog.findViewById(R.id.imgTeamLogo);

                    imgTeamLogo.setVisibility(View.GONE);

                    tvOverUnder.setText("Under");
                    minBtn.setText(String.valueOf(oddsData.point.min));
                    maxBtn.setText(String.valueOf(oddsData.point.max));
                    tvCancel.setOnClickListener(view1 -> dialog.dismiss());

                    tvBet.setOnClickListener(view1 -> {
                        if (minBtn.isChecked()) {
                            betAmount = Integer.parseInt(minBtn.getText().toString());
                        } else if (maxBtn.isChecked()) {
                            betAmount = Integer.parseInt(maxBtn.getText().toString());
                        }

                        onBet(overOddsId,betAmount,2, 2, dialog);
                    } );

                    dialog.show();
                }

            }
        }

    }

    private void onBet(int oddsId , int betAmount, int label, int bettype, Dialog dialog) {

        dialog.dismiss();
//        Log.e("")
        final Disposable subscribe = RetrofitService.getApiEnd().bet(Token.token, typeId, room_id, league.id, league.fixtureId, betAmount, label, bettype,oddsId)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleBetResult, this::handleBetError);

        disposable.add(subscribe);

    }

    private void handleBetError(Throwable throwable) {
        Log.e("bet_failure",throwable.toString());
    }

    private void handleBetResult(BetResponse betResponse) {
        if (betResponse.isSuccess) {
            Toast.makeText(OddsActivity.this, "Bet Successfully.", Toast.LENGTH_LONG).show();
            getOdd();
        } else {
            Toast.makeText(OddsActivity.this, betResponse.errorMessage, Toast.LENGTH_LONG).show();
        }
    }

    public void onBackAction(View view) {

        finish();
    }

    public void onClickAds(View view) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse(imgLink));
        startActivity(intent);

    }

    public void onClickAdsClose(View view) {
        binding.imgAds.setVisibility(View.GONE);
        binding.imgClose.setVisibility(View.GONE);
    }
}
