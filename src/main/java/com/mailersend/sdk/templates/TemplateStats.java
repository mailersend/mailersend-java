/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.templates;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

import com.google.gson.annotations.SerializedName;

public class TemplateStats {
    
    @SerializedName("total")
    public int total;
    
    @SerializedName("processed")
    public int processed;
    
    @SerializedName("queued")
    public int queued;
    
    @SerializedName("sent")
    public int sent;
    
    @SerializedName("rejected")
    public int rejected;
    
    @SerializedName("delivered")
    public int delivered;
    
    public Date lastEmailSentAt;
    
    @SerializedName("last_email_sent_at")
    private String lastEmailSentAtStr;
    
    
    /**
     * Is called to perform any actions after the deserialization of the response
     * Do not call directly
     */
    protected void postDeserialize() {
        
        parseDates();
    }
    
    
    /**
     * Parses the string dates from the response into java.util.Date objects
     */
    protected void parseDates() {
        
        TemporalAccessor ta;
        Instant instant;
        
        if (lastEmailSentAtStr != null && !lastEmailSentAtStr.isBlank()) {
            
            ta = DateTimeFormatter.ISO_INSTANT.parse(lastEmailSentAtStr);
            instant = Instant.from(ta);
            lastEmailSentAt = Date.from(instant);
        }
        
    }
}
