/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.activities;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

import com.google.gson.annotations.SerializedName;

public class Activity {

    @SerializedName("id")
    public String id;
    
    public Date createdAt;
    
    public Date updatedAt;
    
    @SerializedName("type")
    public String type;
    
    @SerializedName("email")
    public ActivityEmail email;
    
    
    @SerializedName("created_at")
    private String createdAtString;
    
    @SerializedName("updated_at")
    private String updatedAtString;
    
    
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
    }
    
    
    /**
     * Is called to perform any actions after the deserialization of the response
     * to the /activities endpoint 
     */
    public void postDeserialize() {
        
        parseDates();
        
        if (email != null) {
            
            email.postDeserialize();
        }
    }
    
}
