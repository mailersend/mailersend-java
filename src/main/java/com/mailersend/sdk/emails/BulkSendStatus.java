/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.emails;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import com.mailersend.sdk.recipients.Recipient;

public class BulkSendStatus {

    @SerializedName("id")
    public String id;
    
    @SerializedName("state")
    public String state;
    
    @SerializedName("total_recipients_count")
    public int totalRecipientsCount;
    
    @SerializedName("suppressed_recipients_count")
    public int suppressedRecipientsCount;
    
    @SerializedName("suppressed_recipients")
    public JsonObject suppressedRecipients;
    
    @SerializedName("validation_errors_count")
    public int validationErrorsCount;
    
    @SerializedName("validation_errors")
    public JsonObject validationErrors;
    
    @SerializedName("messages_id")
    public String[] messagesId;
    
    public Date createdAt;
    
    public Date updatedAt;
    
    @SerializedName("created_at")
    protected String createdAtString;
    
    @SerializedName("updated_at")
    protected String updatedAtString;
    
    
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
}
