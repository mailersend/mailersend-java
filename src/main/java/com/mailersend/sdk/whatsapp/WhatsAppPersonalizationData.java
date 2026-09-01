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
 * <p>WhatsAppPersonalizationData class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class WhatsAppPersonalizationData {

    @SerializedName("header")
    public String[] header;

    @SerializedName("body")
    public String[] body;

    @SerializedName("buttons")
    public String[] buttons;
}
