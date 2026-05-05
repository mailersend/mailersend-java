/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.senderidentities;

import com.google.gson.annotations.SerializedName;
import com.mailersend.sdk.MailerSendResponse;

/**
 * Represents a sender identity returned from the MailerSend API.
 *
 * @author mailersend
 */
public class SenderIdentity extends MailerSendResponse {

    @SerializedName("id")
    public String id;

    @SerializedName("email")
    public String email;

    @SerializedName("name")
    public String name;

    @SerializedName("reply_to_email")
    public String replyToEmail;

    @SerializedName("reply_to_name")
    public String replyToName;

    @SerializedName("is_verified")
    public boolean isVerified;

    @SerializedName("resends")
    public int resends;

    @SerializedName("add_note")
    public boolean addNote;

    @SerializedName("personal_note")
    public String personalNote;

    @SerializedName("domain")
    public SenderIdentityDomain domain;


    /**
     * Is called to perform any actions after deserialization of the response.
     * Do not call directly.
     */
    public void postDeserialize() {

        if (domain != null) {
            domain.postDeserialize();
        }
    }
}
