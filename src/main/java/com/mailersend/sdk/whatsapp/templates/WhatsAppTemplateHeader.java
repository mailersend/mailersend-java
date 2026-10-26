/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.whatsapp.templates;

import com.google.gson.annotations.SerializedName;

/**
 * <p>The header of a WhatsApp template.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class WhatsAppTemplateHeader {

    /**
     * TEXT, IMAGE, VIDEO, DOCUMENT or LOCATION
     */
    @SerializedName("format")
    public String format;

    /**
     * Includes any variable placeholders. Null for a media header
     */
    @SerializedName("text")
    public String text;
}
