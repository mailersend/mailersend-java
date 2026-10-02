/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.whatsapp.inboundmessages;

import com.google.gson.annotations.SerializedName;

/**
 * <p>The content of a <code>button</code> inbound message.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class WhatsAppInboundMessageButton {

    @SerializedName("text")
    public String text;

    @SerializedName("payload")
    public String payload;
}
