/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.whatsapp;

import java.util.Map;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

/**
 * <p>WhatsAppPersonalizationData class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
@JsonAdapter(WhatsAppPersonalizationDataSerializer.class)
public class WhatsAppPersonalizationData {

    @SerializedName("header")
    public String[] header;

    @SerializedName("body")
    public String[] body;

    @SerializedName("buttons")
    public String[] buttons;

    /**
     * Sent as {@code header} for templates with an image, video or document header.
     */
    public transient WhatsAppMediaHeader mediaHeader;

    /**
     * Sent as {@code header} for templates with named header variables.
     */
    public transient Map<String, String> namedHeader;

    /**
     * Sent as {@code body} for templates with named body variables.
     */
    public transient Map<String, String> namedBody;
}
