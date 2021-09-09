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
import java.util.Collections;
import java.util.Date;

import com.google.gson.annotations.SerializedName;
import com.mailersend.sdk.Email;
import com.mailersend.sdk.Recipient;

public class ActivityEmail {

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
    
    @SerializedName("status")
    public String status;
    
    @SerializedName("tags")
    public String[] tags;
    
    public Date createdAt;
    
    public Date updatedAt;
    
    @SerializedName("recipient")
    public ActivityRecipient recipient;
    
    
    @SerializedName("created_at")
    private String createdAtString;
    
    @SerializedName("updated_at")
    private String updatedAtString;
    
    
    /**
     * Is called to perform any actions after the deserialization of the response
     * to the /activities endpoint 
     */
    protected void postDeserialize() {
        
        parseDates();
        
        if (recipient != null) {
            
            recipient.parseDates();
        }
    }
    
    
    /**
     * Converts the retrieved dates to java.util.Date
     */
    private void parseDates() {
        
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
     * Converts this ActivityEmail into a com.mailersend.sdk.Email object
     * @return
     */
    public Email toEmail() {
        
        Email email = new Email();
        email.subject = subject;
        email.from = new Recipient("", from);
        email.html = html;
        email.text = text;
        
        Collections.addAll(email.tags, tags);
        
        email.AddRecipient(recipient.toRecipient());
        
        return email;
    }
}
