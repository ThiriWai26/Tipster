package com.mounts.ballkan.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.mounts.ballkan.data.BetHistoryResponse;
import com.mounts.ballkan.data.Token;
import com.mounts.ballkan.retrofit.RetrofitService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class OnGoingViewModel extends AndroidViewModel {
    private Application application;
    public CompositeDisposable disposable;
    private MutableLiveData<BetHistoryResponse> betData;

    public OnGoingViewModel(@NonNull Application application) {
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
        Disposable subscribe = RetrofitService.getApiEnd().getOngoing(Token.token , 1 , pageNumber)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResult , this::handleError);
        disposable.add(subscribe);
    }
    private void handleResult(BetHistoryResponse betHistoryResponse) {
        betData.setValue(betHistoryResponse);
        Log.e("Ongoinghistroysize", String.valueOf(betHistoryResponse.betHistoryData.size()));
    }
    private void handleError(Throwable throwable) {
        Log.e("history_throwable",throwable.toString());
    }
}
