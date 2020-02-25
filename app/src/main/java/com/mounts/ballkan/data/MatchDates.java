package com.mounts.ballkan.data;

import com.google.gson.annotations.SerializedName;

public class MatchDates {

    @SerializedName("today")
    public String today ;

    @SerializedName("tomorrow")
    public String tomorrow;

    @SerializedName("the_day_after_tomorrow")
    public String afterTomorrow;

}
