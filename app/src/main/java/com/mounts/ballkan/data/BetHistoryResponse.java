package com.mounts.ballkan.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BetHistoryResponse {

    @SerializedName("data")
    public List<BetHistoryData> betHistoryData;

    @SerializedName("next_page_url")
    public String nextPage;
}
