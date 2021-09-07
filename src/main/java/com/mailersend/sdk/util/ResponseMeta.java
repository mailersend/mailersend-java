package com.mailersend.sdk.util;

import com.google.gson.annotations.SerializedName;

public class ResponseMeta {

    @SerializedName("current_page")
    public int currentPage;
    
    @SerializedName("from")
    public int from;
    
    @SerializedName("path")
    public String path;
    
    @SerializedName("per_page")
    public int limit;
    
    @SerializedName("to")
    public int to;
}
