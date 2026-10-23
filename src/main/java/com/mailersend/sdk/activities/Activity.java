/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.activities;

import java.util.Date;

import com.google.gson.annotations.SerializedName;
import com.mailersend.sdk.util.DateParser;

/**
 * <p>Activity class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class Activity {

    @SerializedName("id")
    public String id;
    
    public Date createdAt;
    
    public Date updatedAt;
    
    @SerializedName("type")
    public String type;

    /**
     * Only populated when type is EventTypes.SUPPRESSED. One of: on_hold, hard_bounced,
     * unsubscribed, spam_complained, blocklisted. Null for every other activity type.
     */
    @SerializedName("suppression_reason")
    public String suppressionReason;

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
        
        createdAt = DateParser.parse(createdAtString);
        updatedAt = DateParser.parse(updatedAtString);
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
