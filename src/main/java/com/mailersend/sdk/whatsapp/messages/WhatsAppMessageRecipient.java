/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.whatsapp.messages;

import com.google.gson.annotations.SerializedName;

/**
 * <p>WhatsAppMessageRecipient class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class WhatsAppMessageRecipient {

    @SerializedName("id")
    public String id;

    @SerializedName("to")
    public String to;

    @SerializedName("status")
    public String status;

    @SerializedName("error_code")
    public Integer errorCode;

    @SerializedName("error_message")
    public String errorMessage;

    @SerializedName("activity")
    public WhatsAppMessageActivity[] activity;

    /**
     * <p>postDeserialize.</p>
     */
    public void postDeserialize() {
        if (activity != null) {
            for (WhatsAppMessageActivity a : activity) {
                a.postDeserialize();
            }
        }
    }
}
