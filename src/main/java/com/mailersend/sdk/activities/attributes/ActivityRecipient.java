/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.activities.attributes;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

import com.google.gson.annotations.SerializedName;
import com.mailersend.sdk.Recipient;

public class ActivityRecipient {

    @SerializedName("id")
    public String id = null;
    
    public Date createdAt = null;
    
    public Date updatedAt = null;
    
    public Date deletedAt = null;
    
    @SerializedName("email")
    public String email;
    
    @SerializedName("created_at")
    private String createdAtString;
    
    @SerializedName("updated_at")
    private String updatedAtString;
    
    @SerializedName("deleted_at")
    private String deletedAtString;
    
    
    /**
     * Converts the retrieved dates to java.util.Date
     */
    protected void parseDates() {

        TemporalAccessor ta;
        Instant instant;
        
        if (createdAtString != null && !createdAtString.isBlank()) {
            
            ta = DateTimeFormatter.ISO_INSTANT.parse(createdAtString);
            instant = Instant.from(ta);
            createdAt = Date.from(instant);
        }
        
        if (updatedAtString != null && !updatedAtString.isBlank()) {
            
            ta = DateTimeFormatter.ISO_INSTANT.parse(updatedAtString);
            instant = Instant.from(ta);
            updatedAt = Date.from(instant);
        }
        
        if (deletedAtString != null && !deletedAtString.isBlank()) {
            
            ta = DateTimeFormatter.ISO_INSTANT.parse(deletedAtString);
            instant = Instant.from(ta);
            deletedAt = Date.from(instant);
        }
    }
    
    
    /**
     * Converts this ActivityRecipient to a com.mailersend.sdk.Recipient
     * @return
     */
    public Recipient toRecipient() {
        
        Recipient recipient = new Recipient("", email);
        
        return recipient;
    }
}
