/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.whatsapp.messages;

import com.google.gson.annotations.SerializedName;
import com.mailersend.sdk.util.PaginatedResponse;

/**
 * <p>WhatsAppMessageList class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class WhatsAppMessageList extends PaginatedResponse {

    @SerializedName("data")
    public WhatsAppMessage[] messages;

    /**
     * <p>postDeserialize.</p>
     */
    public void postDeserialize() {
        if (messages != null) {
            for (WhatsAppMessage m : messages) {
                m.postDeserialize();
            }
        }
    }
}
