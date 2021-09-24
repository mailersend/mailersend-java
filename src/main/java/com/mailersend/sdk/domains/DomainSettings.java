/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.domains;

import com.google.gson.annotations.SerializedName;

public class DomainSettings {

    @SerializedName("send_paused")
    public Boolean sendPaused;
    
    @SerializedName("track_clicks")
    public Boolean trackClicks;
    
    @SerializedName("track_opens")
    public Boolean trackOpens;
    
    @SerializedName("track_unsubscribe")
    public Boolean trackUnsubscribe;
    
    @SerializedName("track_unsubscribe_html")
    public String trackUnsubscribeHtml;
    
    @SerializedName("track_unsubscribe_plain")
    public String trackUnsubscribePlain;
    
    @SerializedName("track_content")
    public Boolean trackContent;
    
    @SerializedName("custom_tracking_enabled")
    public Boolean customTrackingEnabled;
    
    @SerializedName("custom_tracking_subdomain")
    public String customTrackingSubdomain;
    
    @SerializedName("return_path_subdomain")
    public String returnPathSubdomain;
    
    @SerializedName("inbound_routing_enabled")
    public Boolean inboundRoutingEnabled;
    
    @SerializedName("inbound_routing_subdomain")
    public String inboundRoutingSubdomain;
    
    
    protected void reset() {
        
        sendPaused = null;
        trackClicks = null;
        trackOpens = null;
        trackUnsubscribe = null;
        trackUnsubscribeHtml = null;
        trackUnsubscribePlain = null;
        trackContent = null;
        customTrackingSubdomain = null;
        returnPathSubdomain = null;
        inboundRoutingEnabled = null;
        inboundRoutingSubdomain = null;
    }
    
}
