/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.activities;

import java.util.Collections;
import java.util.Date;

import com.google.gson.annotations.SerializedName;
import com.mailersend.sdk.Recipient;
import com.mailersend.sdk.emails.Email;
import com.mailersend.sdk.emails.EmailHeader;
import com.mailersend.sdk.util.ApiRecipient;
import com.mailersend.sdk.util.DateParser;

/**
 * <p>ActivityEmail class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class ActivityEmail {

    @SerializedName("id")
    public String id;
    
    @SerializedName("from")
    public String from;
    
    /** Only returned by the single activity endpoint */
    @SerializedName("to")
    public String to;
    
    @SerializedName("subject")
    public String subject;
    
    @SerializedName("text")
    public String text;
    
    @SerializedName("html")
    public String html;
    
    @SerializedName("template_id")
    public String templateId;
    
    /** Only returned by the single activity endpoint */
    @SerializedName("domain_id")
    public String domainId;
    
    /** Only returned by the single activity endpoint */
    @SerializedName("message_id")
    public String messageId;
    
    @SerializedName("status")
    public String status;
    
    @SerializedName("tags")
    public String[] tags;
    
    /** Only returned by the single activity endpoint */
    @SerializedName("interaction")
    public String[] interaction;
    
    /** Only returned by the single activity endpoint */
    @SerializedName("suppression_reason")
    public String suppressionReason;
    
    @SerializedName("headers")
    public EmailHeader[] headers;
    
    public Date createdAt;
    
    public Date updatedAt;
    
    @SerializedName("recipient")
    public ApiRecipient recipient;
    
    
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
        
        createdAt = DateParser.parse(createdAtString);
        updatedAt = DateParser.parse(updatedAtString);
    }
    
    
    /**
     * Converts this ActivityEmail into a com.mailersend.sdk.Email object
     *
     * @return a {@link com.mailersend.sdk.emails.Email} object.
     */
    public Email toEmail() {
        
        Email email = new Email();
        email.subject = subject;
        email.from = new Recipient("", from);
        email.html = html;
        email.text = text;
        
        if (tags != null) {
            
            Collections.addAll(email.tags, tags);
        }
        
        if (recipient != null) {
            
            email.addRecipient(recipient.toRecipient());
        }
        
        return email;
    }
}
