/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.whatsapp;

import com.google.gson.annotations.SerializedName;

/**
 * <p>WhatsAppPersonalization class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class WhatsAppPersonalization {

    @SerializedName("to")
    public String to;

    @SerializedName("data")
    public WhatsAppPersonalizationData data = new WhatsAppPersonalizationData();

    /**
     * <p>Constructor for WhatsAppPersonalization.</p>
     *
     * @param to the recipient phone number
     */
    public WhatsAppPersonalization(String to) {
        this.to = to;
    }

    /**
     * <p>setHeader.</p>
     *
     * @param header array of header variable values
     * @return a {@link com.mailersend.sdk.whatsapp.WhatsAppPersonalization} object.
     */
    public WhatsAppPersonalization setHeader(String[] header) {
        this.data.header = header;
        return this;
    }

    /**
     * <p>setBody.</p>
     *
     * @param body array of body variable values
     * @return a {@link com.mailersend.sdk.whatsapp.WhatsAppPersonalization} object.
     */
    public WhatsAppPersonalization setBody(String[] body) {
        this.data.body = body;
        return this;
    }

    /**
     * <p>setButtons.</p>
     *
     * @param buttons array of button variable values
     * @return a {@link com.mailersend.sdk.whatsapp.WhatsAppPersonalization} object.
     */
    public WhatsAppPersonalization setButtons(String[] buttons) {
        this.data.buttons = buttons;
        return this;
    }
}
