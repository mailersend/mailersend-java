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
 * <p>WhatsAppTemplateButton class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class WhatsAppTemplateButton {

    @SerializedName("type")
    public String type;

    @SerializedName("text")
    public String text;

    /**
     * The URL a URL button opens, otherwise null
     */
    @SerializedName("url")
    public String url;
}
