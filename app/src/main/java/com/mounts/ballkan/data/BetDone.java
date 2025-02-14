package com.mounts.ballkan.data;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.mounts.ballkan.BR;
import com.google.gson.annotations.SerializedName;


public class BetDone extends BaseObservable {

    @SerializedName("over")
    private boolean isOver;

    @SerializedName("under")
    private boolean isUnder;

    @SerializedName("local")
    private boolean isLocal;

    @SerializedName("visitor")
    private boolean isVisitor;



    @Bindable
    public boolean getOver() {
        return isOver;
    }

    public void setOver(boolean over) {
        isOver = over;
        notifyPropertyChanged(BR.odds);

    }

    @Bindable
    public boolean getUnder() {
        return isUnder;
    }

    public void setUnder(boolean under) {
        isUnder = under;
        notifyPropertyChanged(BR.odds);
    }

    @Bindable
    public boolean getLocal() {
        return isLocal;
    }

    public void setLocal(boolean local) {
        isLocal = local;
        notifyPropertyChanged(BR.odds);
    }

    @Bindable
    public boolean getVisitor() {
        return isVisitor;
    }

    public void setVisitor(boolean visitor) {
        isVisitor = visitor;
        notifyPropertyChanged(BR.odds);
    }



}
