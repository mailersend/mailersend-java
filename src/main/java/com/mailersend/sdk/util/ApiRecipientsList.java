/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.util;

import com.google.gson.annotations.SerializedName;

/**
 * <p>ApiRecipientsList class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class ApiRecipientsList extends PaginatedResponse {

    @SerializedName("data")
    public ApiRecipient[] recipients;
    
    
    /**
     * Performs any needed actions after deserialization
     */
    public void postProcessing() {
        
        for (ApiRecipient rec : recipients) {
            
            rec.parseDates();
        }
    }
}
