/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.whatsapp;

import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.whatsapp.activities.WhatsAppActivities;
import com.mailersend.sdk.whatsapp.inboundmessages.WhatsAppInboundMessages;
import com.mailersend.sdk.whatsapp.items.WhatsAppItems;
import com.mailersend.sdk.whatsapp.messages.WhatsAppMessages;
import com.mailersend.sdk.whatsapp.recipients.WhatsAppRecipients;
import com.mailersend.sdk.whatsapp.templates.WhatsAppTemplates;

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
    private WhatsAppItems items;
    private WhatsAppActivities activities;
    private WhatsAppInboundMessages inboundMessages;
    private WhatsAppRecipients recipients;
    private WhatsAppTemplates templates;

    /**
     * <p>Constructor for WhatsApp.</p>
     *
     * @param ref a {@link com.mailersend.sdk.MailerSend} object.
     */
    public WhatsApp(MailerSend ref) {
        apiObjectReference = ref;
        builder = new WhatsAppBuilder(ref);
        messages = new WhatsAppMessages(ref);
        items = new WhatsAppItems(ref);
        activities = new WhatsAppActivities(ref);
        inboundMessages = new WhatsAppInboundMessages(ref);
        recipients = new WhatsAppRecipients(ref);
        templates = new WhatsAppTemplates(ref);
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
     * <p>items.</p>
     *
     * @return a {@link com.mailersend.sdk.whatsapp.items.WhatsAppItems} object.
     */
    public WhatsAppItems items() {
        return items;
    }

    /**
     * <p>activities.</p>
     *
     * @return a {@link com.mailersend.sdk.whatsapp.activities.WhatsAppActivities} object.
     */
    public WhatsAppActivities activities() {
        return activities;
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

    /**
     * <p>templates.</p>
     *
     * @return a {@link com.mailersend.sdk.whatsapp.templates.WhatsAppTemplates} object.
     */
    public WhatsAppTemplates templates() {
        return templates;
    }
}
