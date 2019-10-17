package com.chann.tipster.activity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.chann.tipster.R;
import com.chann.tipster.data.BetResponse;
import com.chann.tipster.data.MatchData;
import com.chann.tipster.data.OddsData;
import com.chann.tipster.data.Token;
import com.chann.tipster.databinding.ActivityOddsBinding;
import com.chann.tipster.retrofit.RetrofitService;
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

    private int typeId = 1, betType, label, betHandicap, betValue, handcap, value;

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
        } else {
            Log.e("response", "fail");
        }
    }



    private void bindData() {

        Picasso.get().load(league.localTeamLogo).resize(50, 50).into(binding.localProfile);
        Picasso.get().load(league.visitorTeamLogo).resize(50, 50).into(binding.visitorProfile);

        Picasso.get().load(league.localTeamLogo).resize(50, 50).into(binding.imgLocalProfile);
        Picasso.get().load(league.visitorTeamLogo).resize(50, 50).into(binding.imgVisitorProfile);

        betHandicap = league.handiCap.handicap;
        betValue = league.handiCap.value;

        handcap = league.overUnder.totalScore;
        value = league.overUnder.value;

        if (league.handiCap.label.equals("Home")) {

            if (league.handiCap.value > 0)
                binding.tvLocalValue.setText(league.handiCap.handicap + "(+" + league.handiCap.value + ")");
            else
                binding.tvLocalValue.setText(league.handiCap.handicap + "(" + league.handiCap.value + ")");

        } else {

            if (league.handiCap.value > 0)
                binding.tvVisitorValue.setText(league.handiCap.handicap + "(+" + league.handiCap.value + ")");
            else {
                binding.tvVisitorValue.setText(league.handiCap.handicap + "(" + league.handiCap.value + ")");
            }
        }

        if (league.overUnder.value > 0) {

            String overValue = String.format("%d(+%d)", league.overUnder.totalScore, league.overUnder.value);
            binding.tvOver.setText(overValue);
            binding.tvUnder.setText(overValue);

        } else {

            String overValue = String.format("%d(%d)", league.overUnder.totalScore, league.overUnder.value);
            binding.tvOver.setText(overValue);
            binding.tvUnder.setText(overValue);
        }


    }

    public void onLocalTeamBetClick(View view) {

        betType = 1;
        label = 1;

        final Dialog dialog = new Dialog(this);
        final RadioButton minBtn, maxBtn;
        ImageView imgTeamLogo;
        TextView tvCancel, tvBet, tvHandicapValue, labelOverUnder, tvOverUnderValue;

        dialog.setContentView(R.layout.dialog_bet);
        imgTeamLogo = dialog.findViewById(R.id.imgTeamLogo);
        tvCancel = dialog.findViewById(R.id.tvCancel);
        tvBet = dialog.findViewById(R.id.tvBet);
        tvHandicapValue = dialog.findViewById(R.id.tvHandicapValue);
        minBtn = dialog.findViewById(R.id.minAmount);
        maxBtn = dialog.findViewById(R.id.maxAmount);
        labelOverUnder = dialog.findViewById(R.id.labelOverUnder);
        tvOverUnderValue = dialog.findViewById(R.id.tvOverUnderValue);

        labelOverUnder.setVisibility(View.GONE);
        tvOverUnderValue.setVisibility(View.GONE);

        Picasso.get().load(league.localTeamLogo).resize(50, 50).into(imgTeamLogo);
        tvHandicapValue.setText(binding.tvLocalValue.getText().toString());
        minBtn.setText(String.valueOf(oddsData.point.min));
        maxBtn.setText(String.valueOf(oddsData.point.max));


        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        tvBet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.e("bet", "success");

                if (minBtn.isChecked()) {
                    betAmount = Integer.parseInt(minBtn.getText().toString());
                    Log.e("min_bet_amount", String.valueOf(betAmount));
                } else if (maxBtn.isChecked()) {
                    betAmount = Integer.parseInt(maxBtn.getText().toString());
                    Log.e("max_bet_amount", String.valueOf(betAmount));
                }

                onBet(betAmount, betHandicap, betValue, 1, 1, dialog);

            }
        });

        dialog.show();


    }

    public void onVisitorTeamBetClick(View view) {
        betType = 1;
        label = 2;

        final Dialog dialog = new Dialog(this);
        final RadioButton minBtn, maxBtn;
        ImageView imgTeamLogo;
        TextView tvCancel, tvBet, tvHandicapValue, labelOverUnder, tvOverUnderValue;

        dialog.setContentView(R.layout.dialog_bet);
        imgTeamLogo = dialog.findViewById(R.id.imgTeamLogo);
        tvCancel = dialog.findViewById(R.id.tvCancel);
        tvBet = dialog.findViewById(R.id.tvBet);
        tvHandicapValue = dialog.findViewById(R.id.tvHandicapValue);
        minBtn = dialog.findViewById(R.id.minAmount);
        maxBtn = dialog.findViewById(R.id.maxAmount);

        Picasso.get().load(league.visitorTeamLogo).resize(50, 50).into(imgTeamLogo);
        tvHandicapValue.setText(binding.tvVisitorValue.getText().toString());
        minBtn.setText(String.valueOf(oddsData.point.min));
        maxBtn.setText(String.valueOf(oddsData.point.max));

        labelOverUnder = dialog.findViewById(R.id.labelOverUnder);
        tvOverUnderValue = dialog.findViewById(R.id.tvOverUnderValue);

        labelOverUnder.setVisibility(View.GONE);
        tvOverUnderValue.setVisibility(View.GONE);

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
            }
        });

        tvBet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (minBtn.isChecked()) {
                    betAmount = Integer.parseInt(minBtn.getText().toString());
                } else if (maxBtn.isChecked()) {
                    betAmount = Integer.parseInt(maxBtn.getText().toString());
                }

                onBet(betAmount, betHandicap, betValue, 2, 1, dialog);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void onOverBetClick(View view) {
        betType = 2;
        label = 1;

        final Dialog dialog = new Dialog(this);
        final RadioButton minBtn, maxBtn;
        TextView tvOverUnder, tvCancel, tvBet, tvOverUnderValue, tvHanddicapValue;
        ImageView imgTeamLogo;

        dialog.setContentView(R.layout.dialog_bet);
        tvOverUnder = dialog.findViewById(R.id.labelOverUnder);
        tvCancel = dialog.findViewById(R.id.tvCancel);
        tvBet = dialog.findViewById(R.id.tvBet);
        tvOverUnderValue = dialog.findViewById(R.id.tvOverUnderValue);
        minBtn = dialog.findViewById(R.id.minAmount);
        maxBtn = dialog.findViewById(R.id.maxAmount);
        tvHanddicapValue = dialog.findViewById(R.id.tvHandicapValue);
        imgTeamLogo = dialog.findViewById(R.id.imgTeamLogo);

        tvHanddicapValue.setVisibility(View.GONE);
        imgTeamLogo.setVisibility(View.GONE);

        tvOverUnder.setText("Over");
        tvOverUnderValue.setText(binding.tvOver.getText().toString());
        minBtn.setText(String.valueOf(oddsData.point.min));
        maxBtn.setText(String.valueOf(oddsData.point.max));

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        tvBet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (minBtn.isChecked()) {
                    betAmount = Integer.parseInt(minBtn.getText().toString());
                } else if (maxBtn.isChecked()) {
                    betAmount = Integer.parseInt(maxBtn.getText().toString());
                }

                onBet(betAmount, handcap, value, 1, 2, dialog);
            }
        });
        dialog.show();

    }

    public void onUnderBetClick(View view) {
        betType = 2;
        label = 2;

        final Dialog dialog = new Dialog(this);
        final RadioButton minBtn, maxBtn;
        TextView tvOverUnder, tvCancel, tvBet, tvOverUnderValue, tvHandicapValue;
        ImageView imgTeamLogo;

        dialog.setContentView(R.layout.dialog_bet);
        tvCancel = dialog.findViewById(R.id.tvCancel);
        tvBet = dialog.findViewById(R.id.tvBet);

        tvOverUnder = dialog.findViewById(R.id.labelOverUnder);
        tvOverUnderValue = dialog.findViewById(R.id.tvOverUnderValue);
        minBtn = dialog.findViewById(R.id.minAmount);
        maxBtn = dialog.findViewById(R.id.maxAmount);
        tvHandicapValue = dialog.findViewById(R.id.tvHandicapValue);
        imgTeamLogo = dialog.findViewById(R.id.imgTeamLogo);

        tvHandicapValue.setVisibility(View.GONE);
        imgTeamLogo.setVisibility(View.GONE);

        tvOverUnder.setText("Under");
        tvOverUnderValue.setText(binding.tvUnder.getText().toString());
        minBtn.setText(String.valueOf(oddsData.point.min));
        maxBtn.setText(String.valueOf(oddsData.point.max));

        tvCancel.setOnClickListener(view1 -> dialog.dismiss());

        tvBet.setOnClickListener(view1 -> {
            if (minBtn.isChecked()) {
                betAmount = Integer.parseInt(minBtn.getText().toString());
            } else if (maxBtn.isChecked()) {
                betAmount = Integer.parseInt(maxBtn.getText().toString());
            }
            onBet(betAmount, handcap, value, 2, 2, dialog);
        } );

        dialog.show();
    }

    private void onBet(int betAmount, int betHandicap, int betValue, int label, int bettype, Dialog dialog) {

        dialog.dismiss();
//        Log.e("")
        final Disposable subscribe = RetrofitService.getApiEnd().bet(Token.token, typeId, room_id, league.id, league.fixtureId, betAmount, betHandicap, betValue, label, betType)
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
        } else {
            Toast.makeText(OddsActivity.this, betResponse.errorMessage, Toast.LENGTH_LONG).show();
        }
    }

    public void onBackAction(View view) {

        finish();
    }
}
