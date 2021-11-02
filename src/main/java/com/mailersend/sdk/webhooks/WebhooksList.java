/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.webhooks;

import com.google.gson.annotations.SerializedName;
import com.mailersend.sdk.util.PaginatedResponse;

public class WebhooksList extends PaginatedResponse {

    @SerializedName("data")
    public Webhook[] webhooks;
    
    
    protected void postDeserialize() {
        
        for (Webhook webhook : webhooks) {
            
            webhook.postDeserialize();
        }
        
    }
}
