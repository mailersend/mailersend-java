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
 * <p>The content of a <code>location</code> inbound message.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class WhatsAppInboundMessageLocation {

    @SerializedName("latitude")
    public double latitude;

    @SerializedName("longitude")
    public double longitude;

    @SerializedName("name")
    public String name;

    @SerializedName("address")
    public String address;
}
