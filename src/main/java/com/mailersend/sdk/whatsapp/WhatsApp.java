/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.whatsapp;

import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.whatsapp.inboundmessages.WhatsAppInboundMessages;
import com.mailersend.sdk.whatsapp.messages.WhatsAppMessages;
import com.mailersend.sdk.whatsapp.recipients.WhatsAppRecipients;

/**
 * <p>WhatsApp class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class WhatsApp {

    private MailerSend apiObjectReference;
    private WhatsAppBuilder builder;
    private WhatsAppMessages messages;
    private WhatsAppInboundMessages inboundMessages;
    private WhatsAppRecipients recipients;

    /**
     * <p>Constructor for WhatsApp.</p>
     *
     * @param ref a {@link com.mailersend.sdk.MailerSend} object.
     */
    public WhatsApp(MailerSend ref) {
        apiObjectReference = ref;
        builder = new WhatsAppBuilder(ref);
        messages = new WhatsAppMessages(ref);
        inboundMessages = new WhatsAppInboundMessages(ref);
        recipients = new WhatsAppRecipients(ref);
    }

    /**
     * <p>builder.</p>
     *
     * @return a {@link com.mailersend.sdk.whatsapp.WhatsAppBuilder} object.
     */
    public WhatsAppBuilder builder() {
        return builder;
    }

    /**
     * <p>messages.</p>
     *
     * @return a {@link com.mailersend.sdk.whatsapp.messages.WhatsAppMessages} object.
     */
    public WhatsAppMessages messages() {
        return messages;
    }

    /**
     * <p>inboundMessages.</p>
     *
     * @return a {@link com.mailersend.sdk.whatsapp.inboundmessages.WhatsAppInboundMessages} object.
     */
    public WhatsAppInboundMessages inboundMessages() {
        return inboundMessages;
    }

    /**
     * <p>recipients.</p>
     *
     * @return a {@link com.mailersend.sdk.whatsapp.recipients.WhatsAppRecipients} object.
     */
    public WhatsAppRecipients recipients() {
        return recipients;
    }
}
