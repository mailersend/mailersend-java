/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.whatsapp;

import java.util.Map;

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
     * @param to the recipient the values apply to, as listed in the message's recipients
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
        this.data.mediaHeader = null;
        this.data.namedHeader = null;
        return this;
    }

    /**
     * <p>setMediaHeader.</p>
     *
     * @param link the public https URL of the image, video or document to show in the header
     * @return a {@link com.mailersend.sdk.whatsapp.WhatsAppPersonalization} object.
     */
    public WhatsAppPersonalization setMediaHeader(String link) {
        return setMediaHeader(link, null);
    }

    /**
     * <p>setMediaHeader.</p>
     *
     * @param link the public https URL of the image, video or document to show in the header
     * @param filename the file name shown for a document header
     * @return a {@link com.mailersend.sdk.whatsapp.WhatsAppPersonalization} object.
     */
    public WhatsAppPersonalization setMediaHeader(String link, String filename) {
        this.data.mediaHeader = new WhatsAppMediaHeader(link, filename);
        this.data.header = null;
        this.data.namedHeader = null;
        return this;
    }

    /**
     * <p>setNamedHeader.</p>
     *
     * @param header header variable values keyed by variable name
     * @return a {@link com.mailersend.sdk.whatsapp.WhatsAppPersonalization} object.
     */
    public WhatsAppPersonalization setNamedHeader(Map<String, String> header) {
        this.data.namedHeader = header;
        this.data.header = null;
        this.data.mediaHeader = null;
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
        this.data.namedBody = null;
        return this;
    }

    /**
     * <p>setNamedBody.</p>
     *
     * @param body body variable values keyed by variable name
     * @return a {@link com.mailersend.sdk.whatsapp.WhatsAppPersonalization} object.
     */
    public WhatsAppPersonalization setNamedBody(Map<String, String> body) {
        this.data.namedBody = body;
        this.data.body = null;
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
