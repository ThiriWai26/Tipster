package com.mounts.ballkan.data;

import com.google.gson.annotations.SerializedName;

public class BetResponse {

    public boolean isSuccess;

    @SerializedName("error_message")
    public String errorMessage;
}
