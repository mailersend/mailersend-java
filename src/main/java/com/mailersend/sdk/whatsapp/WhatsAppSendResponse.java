/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.whatsapp;

import com.google.gson.annotations.SerializedName;
import com.mailersend.sdk.MailerSendResponse;

/**
 * <p>WhatsAppSendResponse class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class WhatsAppSendResponse extends MailerSendResponse {

    @SerializedName("data")
    public WhatsAppSendResponseData data;
}
