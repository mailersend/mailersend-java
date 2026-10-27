/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.whatsapp.messages;

import com.google.gson.annotations.SerializedName;
import com.mailersend.sdk.MailerSendResponse;

class SingleWhatsAppMessageResponse extends MailerSendResponse {

    @SerializedName("data")
    public WhatsAppMessage message;
}
