/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.webhooks;

import com.google.gson.annotations.SerializedName;
import com.mailersend.sdk.MailerSendResponse;

class WebhookResponse extends MailerSendResponse {

    @SerializedName("data")
    public Webhook webhook;
    
    void postDeserialize() {
        
        if (webhook != null) {
            
            webhook.postDeserialize();
        }
    }
}
