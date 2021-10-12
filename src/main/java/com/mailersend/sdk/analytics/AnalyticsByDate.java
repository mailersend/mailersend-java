/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.analytics;

import java.util.Date;

import com.google.gson.annotations.SerializedName;

public class AnalyticsByDate {

    @SerializedName("date")
    private String dateString;
    
    public Date statDate;
    
    @SerializedName("processed")
    public int processed;
    
    @SerializedName("queued")
    public int queued;
    
    @SerializedName("sent")
    public int sent;
    
    @SerializedName("delivered")
    public int delivered;
    
    @SerializedName("soft_bounced")
    public int softBounced;
    
    @SerializedName("hard_bounced")
    public int hardBounced;
    
    @SerializedName("junk")
    public int junk;
    
    @SerializedName("opened")
    public int opened;
    
    @SerializedName("clicked")
    public int clicked;
    
    @SerializedName("unsubscribed")
    public int unsubscribed;
    
    @SerializedName("spam_complaints")
    public int spamComplaints;
    
    
    public void postDeserialize() {
        
        parseDates();
    }
    
    
    /**
     * Converts the retrieved timestamps to java.util.Date
     */
    private void parseDates() {
        
        if (dateString != null && !dateString.isBlank()) {
   
            statDate = new Date(Long.parseLong(dateString) * 1000);
        }
    }
}
