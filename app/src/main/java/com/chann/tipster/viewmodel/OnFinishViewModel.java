package com.chann.tipster.viewmodel;

import android.app.Application;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.chann.tipster.data.BetHistoryResponse;
import com.chann.tipster.data.Token;
import com.chann.tipster.retrofit.RetrofitService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class OnFinishViewModel extends AndroidViewModel {

    private Application application;
    public CompositeDisposable disposable;
    private MutableLiveData<BetHistoryResponse> betData;

    public OnFinishViewModel(@NonNull Application application) {
        super(application);

        this.application = application;
        disposable = new CompositeDisposable();
    }

    public LiveData<BetHistoryResponse> getData(int pageNumber){
        if(betData == null){
            betData = new MutableLiveData<>();
        }
        loadData(pageNumber);
        return betData;
    }

    private void loadData(int pageNumber) {

        Disposable subscribe = RetrofitService.getApiEnd().getOnfinish(Token.token , 1 , pageNumber)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResult , this::handleError);
        disposable.add(subscribe);
    }
    private void handleError(Throwable throwable) {
        Log.e("history_throwable",throwable.toString());
    }

    private void handleResult(BetHistoryResponse betHistoryResponse) {

        this.betData.setValue(betHistoryResponse);
    }
}
