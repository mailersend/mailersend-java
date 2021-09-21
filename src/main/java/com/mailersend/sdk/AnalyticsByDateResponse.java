package com.mailersend.sdk;

import com.google.gson.annotations.SerializedName;
import com.mailersend.sdk.analytics.AnalyticsByDate;

class AnalyticsByDateResponse extends MailerSendResponse {

    @SerializedName("data")
    public AnalyticsByDateList response;
    
    
    protected void postDeserialize() {
        
        response.postDeserialize();
    }
}
