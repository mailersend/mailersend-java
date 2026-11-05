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
 * <p>WhatsAppMediaHeader class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class WhatsAppMediaHeader {

    @SerializedName("link")
    public String link;

    @SerializedName("filename")
    public String filename;

    /**
     * <p>Constructor for WhatsAppMediaHeader.</p>
     *
     * @param link the public https URL of the image, video or document to show in the header
     * @param filename the file name shown for a document header, or null
     */
    public WhatsAppMediaHeader(String link, String filename) {
        this.link = link;
        this.filename = filename;
    }
}
