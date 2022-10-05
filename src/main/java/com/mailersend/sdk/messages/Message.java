/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.messages;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

import com.google.gson.annotations.SerializedName;
import com.mailersend.sdk.MailerSendResponse;
import com.mailersend.sdk.domains.Domain;
import com.mailersend.sdk.util.ApiEmail;

/**
 * <p>Message class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class Message {

    @SerializedName("id")
    public String id;
    
    public Date createdAt;
    public Date updatedAt;
    
    @SerializedName("created_at")
    protected String createdAtString;
    
    @SerializedName("updated_at")
    protected String updatedAtString;
    
    @SerializedName("emails")
    public ApiEmail[] emails;
    
    @SerializedName("domain")
    public Domain domain;
    
    
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
        
        if (domain != null) {
            
            domain.postDeserialize();
        }
        
        for (ApiEmail email : emails) {
            
            email.parseDates();
        }
    }
}
