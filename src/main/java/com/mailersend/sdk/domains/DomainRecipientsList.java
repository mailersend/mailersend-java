package com.mailersend.sdk.domains;

import com.google.gson.annotations.SerializedName;
import com.mailersend.sdk.util.ApiRecipient;
import com.mailersend.sdk.util.PaginatedResponse;

public class DomainRecipientsList extends PaginatedResponse{

    @SerializedName("data")
    public ApiRecipient[] recipients;
    
    public void postProcessing() {
        
        for (ApiRecipient rec : recipients) {
            
            rec.parseDates();
        }
    }
}
