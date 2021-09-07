package com.mailersend.sdk.util;

import com.google.gson.annotations.SerializedName;

public class ResponseLinks {

    @SerializedName("first")
    public String first;
    
    @SerializedName("last")
    public String last;
    
    @SerializedName("prev")
    public String prev;
    
    @SerializedName("next")
    public String next;
}
