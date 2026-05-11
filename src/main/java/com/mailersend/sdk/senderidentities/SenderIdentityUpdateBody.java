/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.senderidentities;

import com.google.gson.annotations.SerializedName;

/**
 * Request body for PUT (update) sender identity calls.
 * Only the fields accepted by the API on update are included:
 * {@code name}, {@code reply_to_email}, and {@code reply_to_name}.
 */
class SenderIdentityUpdateBody {

    @SerializedName("name")
    public String name = null;

    @SerializedName("reply_to_email")
    public String replyToEmail = null;

    @SerializedName("reply_to_name")
    public String replyToName = null;
}
