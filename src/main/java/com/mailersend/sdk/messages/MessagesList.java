/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.messages;

import com.google.gson.annotations.SerializedName;
import com.mailersend.sdk.util.PaginatedResponse;

/**
 * <p>MessagesList class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class MessagesList extends PaginatedResponse {

    @SerializedName("data")
    public MessagesListItem[] messages;
    
    
    /**
     * Performs any needed actions after deserialization
     */
    protected void postProcessing() {
        
        for (MessagesListItem rec : messages) {
            
            rec.parseDates();
        }
    }
}
