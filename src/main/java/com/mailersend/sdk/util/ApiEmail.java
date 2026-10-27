/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.util;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

import com.google.gson.annotations.SerializedName;
import com.mailersend.sdk.Recipient;

/**
 * <p>ApiEmail class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class ApiEmail {

    @SerializedName("id")
    public String id;
    
    @SerializedName("from")
    public String from;

    @SerializedName("subject")
    public String subject;

    @SerializedName("text")
    public String text;

    @SerializedName("html")
    public String html;
    
    @SerializedName("tags")
    public String[] tags;
    
    @SerializedName("status")
    public String status;
    
    @SerializedName("created_at")
    private String createdAtString;
    
    @SerializedName("updated_at")
    private String updatedAtString;
    
    public Date createdAt;
    
    public Date updatedAt;
    
    
    /**
     * Converts the retrieved dates to java.util.Date
     */
    public void parseDates() {

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
}
