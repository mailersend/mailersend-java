/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.whatsapp.inboundmessages;

import com.google.gson.annotations.SerializedName;

/**
 * <p>Present when the inbound message is a reply. <code>messageId</code> is the WhatsApp ID of the message that was replied to.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class WhatsAppInboundMessageContext {

    @SerializedName("message_id")
    public String messageId;
}
