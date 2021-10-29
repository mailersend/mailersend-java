/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.webhooks;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

import com.google.gson.annotations.SerializedName;
import com.mailersend.sdk.domains.Domain;

public class Webhook {

    @SerializedName("id")
    public String id;
    
    @SerializedName("url")
    public String url;
    
    @SerializedName("events")
    public String[] events;
    
    @SerializedName("name")
    public String name;
    
    @SerializedName("enabled")
    public Boolean enabled;
    
    @SerializedName("editable")
    public Boolean editable;
    
    public Date createdAt;
    
    @SerializedName("created_at")
    private String createdAtStr;
    
    public Date updatedAt;
    
    @SerializedName("updated_at")
    private String updatedAtStr;
    
    @SerializedName("domain")
    public Domain domain;
    
    
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
        
        if (createdAtStr != null && !createdAtStr.isBlank()) {
            
            ta = DateTimeFormatter.ISO_INSTANT.parse(createdAtStr);
            instant = Instant.from(ta);
            createdAt = Date.from(instant);
        }
        
        if (updatedAtStr != null && !updatedAtStr.isBlank()) {
            
            ta = DateTimeFormatter.ISO_INSTANT.parse(updatedAtStr);
            instant = Instant.from(ta);
            updatedAt = Date.from(instant);
        }
        
        if (domain != null) {
        
            domain.postDeserialize();
        }
    }
}
