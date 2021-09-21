package com.mailersend.sdk;

import com.google.gson.annotations.SerializedName;

public class AnalyticsStatistic {

    @SerializedName("name")
    public String name;
    
    @SerializedName("count")
    public int count;
}
