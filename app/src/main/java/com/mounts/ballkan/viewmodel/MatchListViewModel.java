package com.mounts.ballkan.viewmodel;

import android.app.Application;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.mounts.ballkan.data.MatchListResponse;
import com.mounts.ballkan.data.Token;
import com.mounts.ballkan.retrofit.RetrofitService;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MatchListViewModel extends AndroidViewModel {
    private Application application;
    public CompositeDisposable compositeDisposable;
    public Disposable disposable;
    private MutableLiveData<MatchListResponse> matchListResponse;
    private String date;


    public MatchListViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        compositeDisposable = new CompositeDisposable();


    }


    public LiveData<MatchListResponse> getMatchList(String date){

        this.date = date;

        if(matchListResponse == null){
            matchListResponse = new MutableLiveData<>();
        }
        disposable = Observable.interval(1000, 30000,
                TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::loadData);
        return matchListResponse;
    }

    private void loadData(Long aLong) {
        Disposable subscribe = RetrofitService.getApiEnd().getMatchList(Token.token , date)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResult , this::handleError);

        compositeDisposable.add(subscribe);
    }

    private void handleError(Throwable throwable) {
        Log.e("failure", throwable.toString());
    }

    private void handleResult(MatchListResponse matchListResponse) {

        this.matchListResponse.setValue(matchListResponse);

    }


}
