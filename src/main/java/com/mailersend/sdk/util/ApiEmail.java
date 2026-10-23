/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.util;

import java.util.Date;

import com.google.gson.annotations.SerializedName;
import com.mailersend.sdk.emails.EmailHeader;

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
    
    @SerializedName("domain_id")
    public String domainId;
    
    @SerializedName("message_id")
    public String messageId;
    
    @SerializedName("tags")
    public String[] tags;
    
    @SerializedName("status")
    public String status;
    
    @SerializedName("interaction")
    public String[] interaction;
    
    @SerializedName("suppression_reason")
    public String suppressionReason;
    
    @SerializedName("headers")
    public EmailHeader[] headers;
    
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

        createdAt = DateParser.parse(createdAtString);
        updatedAt = DateParser.parse(updatedAtString);
    }
}
