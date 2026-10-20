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
 * <p>The header or body of a WhatsApp template.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class WhatsAppTemplateText {

    /**
     * Includes any variable placeholders. Null when the component has no text, such as a media header
     */
    @SerializedName("text")
    public String text;
}
