package com.mailersend.sdk;

import com.google.gson.annotations.SerializedName;

class AnalyticsResponse extends MailerSendResponse {

    @SerializedName("data")
    public AnalyticsList response;
    
    
    protected void postDeserialize() {
        
        response.postDeserialize();
    }
}
