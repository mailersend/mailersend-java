package com.mailersend.sdk.recipients;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class SuppressionAddBody {

    @SerializedName("domain_id")
    public String domainId;
    
    @SerializedName("recipients")
    public ArrayList<String> recipients = new ArrayList<String>();
    
    /**
     * Resets the values so that the object can be reused
     */
    public void reset() {
        
        domainId = null;
        recipients.clear();
    }
}
