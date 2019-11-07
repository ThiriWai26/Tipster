package com.chann.tipster.viewmodel;

import android.app.Application;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.chann.tipster.R;
import com.chann.tipster.data.StandingResponse;
import com.chann.tipster.data.Token;
import com.chann.tipster.retrofit.RetrofitService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RankViewModel extends AndroidViewModel {

    private Application application;
    public CompositeDisposable disposable;
    private MutableLiveData<StandingResponse> rankData;

    public RankViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        disposable = new CompositeDisposable();

    }

    public LiveData<StandingResponse> getData(int pageNumber){

        if(rankData == null){
            rankData = new MutableLiveData<>();
        }
        loadData(pageNumber);

        return rankData;
    }

    private void loadData(int pageNumber) {
        Disposable subscribe = RetrofitService.getApiEnd().getUserStanding(Token.token, 1, 1 , pageNumber)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResult, this::handleError);
        disposable.add(subscribe);
    }

    private void handleResult(StandingResponse standingResponse) {
        rankData.setValue(standingResponse);
    }

    private void handleError(Throwable throwable) {
        Log.e("onfailure",throwable.toString());
    }

}
