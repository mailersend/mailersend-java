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
import com.mailersend.sdk.Recipient;

/**
 * <p>ApiRecipient class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class ApiRecipient {

    @SerializedName("id")
    public String id = null;
    
    public Date createdAt = null;
    
    public Date updatedAt = null;
    
    public Date deletedAt = null;
    
    @SerializedName("email")
    public String email;
    
    @SerializedName("created_at")
    private String createdAtString;
    
    @SerializedName("updated_at")
    private String updatedAtString;
    
    @SerializedName("deleted_at")
    private String deletedAtString;
    
    
    /**
     * Converts the retrieved dates to java.util.Date
     */
    public void parseDates() {

        createdAt = DateParser.parse(createdAtString);
        updatedAt = DateParser.parse(updatedAtString);
        deletedAt = DateParser.parse(deletedAtString);
    }
    
    
    /**
     * Converts this ApiRecipient to a com.mailersend.sdk.Recipient
     *
     * @return a {@link com.mailersend.sdk.Recipient} object.
     */
    public Recipient toRecipient() {
        
        Recipient recipient = new Recipient("", email);
        
        return recipient;
    }
}
