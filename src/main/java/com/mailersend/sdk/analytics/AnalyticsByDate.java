package com.mailersend.sdk.analytics;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

import com.google.gson.annotations.SerializedName;

public class AnalyticsByDate {

    @SerializedName("date")
    private String dateString;
    
    public Date date;
    
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
     * Converts the retrieved dates to java.util.Date
     */
    private void parseDates() {
        
        TemporalAccessor ta;
        Instant instant;
        
        if (dateString != null && !dateString.isBlank()) {
            
            ta = DateTimeFormatter.ISO_INSTANT.parse(dateString);
            instant = Instant.from(ta);
            date = Date.from(instant);
        }
    }
}
