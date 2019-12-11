package com.chann.tipster.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class HomeViewModel extends AndroidViewModel{
    public BtnLeagueClickListener listener;
    public HomeViewModel(@NonNull Application application) {
        super(application);
    }
    // TODO: Implement the ViewModel

    public void onClickEvent(String fragmentName){
        listener.onLeagueClick(fragmentName);
    }

    public interface BtnLeagueClickListener{
        public void onLeagueClick(String league);
    }
}
