package com.mounts.ballkan.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.mounts.ballkan.data.StandingResponse;
import com.mounts.ballkan.data.Token;
import com.mounts.ballkan.retrofit.RetrofitService;

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
