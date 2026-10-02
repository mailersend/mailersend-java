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
 * <p>The content of an <code>interactive</code> inbound message that is a list reply.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class WhatsAppInboundMessageListReply {

    @SerializedName("id")
    public String id;

    @SerializedName("title")
    public String title;

    @SerializedName("description")
    public String description;
}
