/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.whatsapp.inboundmessages;

import com.google.gson.annotations.SerializedName;
import com.mailersend.sdk.util.PaginatedResponse;

/**
 * <p>WhatsAppInboundMessageList class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class WhatsAppInboundMessageList extends PaginatedResponse {

    @SerializedName("data")
    public WhatsAppInboundMessage[] inboundMessages;

    /**
     * <p>postDeserialize.</p>
     */
    public void postDeserialize() {
        if (inboundMessages != null) {
            for (WhatsAppInboundMessage m : inboundMessages) {
                m.postDeserialize();
            }
        }
    }
}
