/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.recipients;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

import com.google.gson.annotations.SerializedName;
import com.mailersend.sdk.domains.Domain;

/**
 * <p>Recipient class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class Recipient {


    @SerializedName("id")
    public String id;

    @SerializedName("email")
    public String email;
    
    @SerializedName("created_at")
    private String createdAtString;
    
    @SerializedName("updated_at")
    private String updatedAtString;
    
    @SerializedName("deleted_at")
    private String deletedAtString;
    
    @SerializedName("domain")
    public Domain domain;
    
    public Date createdAt;
    
    public Date updatedAt;
    
    public Date deletedAt;
    
    
    
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
        
        domain.postDeserialize();
    }
    
}
