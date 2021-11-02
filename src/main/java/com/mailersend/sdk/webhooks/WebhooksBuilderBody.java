/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.webhooks;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

class WebhooksBuilderBody {

    @SerializedName("url")
    public String url;
    
    @SerializedName("name")
    public String name;
    
    @SerializedName("events")
    public ArrayList<String> events = new ArrayList<String>();
    
    @SerializedName("enabled")
    public Boolean enabled = null;
    
    @SerializedName("domain_id")
    public String domainId;
    
    
    /**
     * Resets the values so that the object can be reused
     */
    public void reset() {
        
        url = null;
        
        name = null;
        
        events = new ArrayList<String>();
        
        enabled = null;
        
        domainId = null;
    }
}
