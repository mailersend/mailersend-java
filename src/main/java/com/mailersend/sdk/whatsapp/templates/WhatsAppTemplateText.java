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
 * <p>The body of a WhatsApp template.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class WhatsAppTemplateText {

    /**
     * Includes any variable placeholders. Null when the body has no text
     */
    @SerializedName("text")
    public String text;
}
