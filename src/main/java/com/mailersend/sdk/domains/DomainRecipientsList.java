/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.domains;

import com.google.gson.annotations.SerializedName;
import com.mailersend.sdk.util.ApiRecipient;
import com.mailersend.sdk.util.PaginatedResponse;

public class DomainRecipientsList extends PaginatedResponse{

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
