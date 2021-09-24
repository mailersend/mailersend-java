/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.domains;

import java.util.Date;

import com.google.gson.annotations.SerializedName;
import com.mailersend.sdk.MailerSendResponse;

public class Domain extends MailerSendResponse {

    
    @SerializedName("id")
    public String id;
    
    @SerializedName("name")
    public String name;
    
    @SerializedName("dkim")
    public boolean dkim;
    
    @SerializedName("spf")
    public boolean spf;
    
    @SerializedName("tracking")
    public boolean tracking;
    
    @SerializedName("is_verified")
    public boolean isVerified;
    
    @SerializedName("is_cname_verified")
    public boolean isCnameVerified;
    
    @SerializedName("is_dns_active")
    public boolean isDnsActive;
    
    @SerializedName("is_cname_active")
    public boolean isCnameActive;
    
    @SerializedName("is_tracking_allowed")
    public boolean isTrackingAllowed;
    
    @SerializedName("has_not_queued_messaged")
    public boolean hasNotQueuedMessaged;
    
    @SerializedName("not_queued_messages_count")
    public int notQueuedMessagesCount;
    
    @SerializedName("domain_settings")
    public DomainSettings domainSettings;
    
    @SerializedName("created_at")
    private String createdAtStr;
    
    @SerializedName("updated_at")
    private String updatedAtStr;
    
    public Date createdAt;
    
    public Date updatedAt;
    
    
    public void postDeserialize() {
        
    }
    
    
    private void parseDates() {
        
        
    }
}
