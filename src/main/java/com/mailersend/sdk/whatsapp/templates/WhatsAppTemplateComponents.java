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
 * <p>WhatsAppTemplateComponents class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class WhatsAppTemplateComponents {

    /**
     * Null when the template has no header
     */
    @SerializedName("header")
    public WhatsAppTemplateHeader header;

    /**
     * Null when the template has no body
     */
    @SerializedName("body")
    public WhatsAppTemplateText body;

    /**
     * Empty when the template has no buttons
     */
    @SerializedName("buttons")
    public WhatsAppTemplateButton[] buttons;
}
