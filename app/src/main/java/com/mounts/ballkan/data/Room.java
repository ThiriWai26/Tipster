package com.mounts.ballkan.data;

import com.google.gson.annotations.SerializedName;

public class Room {

    @SerializedName("id")
    public Integer roomId;

     @SerializedName("name")
    public String name;

     @SerializedName("start_date")
    public String startDate;

     @SerializedName("start_time")
    public String startTime;

     @SerializedName("is_active")
    public boolean isActive;
}
