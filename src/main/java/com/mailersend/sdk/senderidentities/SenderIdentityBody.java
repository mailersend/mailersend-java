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
 * Request body used by SenderIdentityBuilder for create/update calls.
 * Do not use directly.
 */
class SenderIdentityBody {

    @SerializedName("domain_id")
    public String domainId = null;

    @SerializedName("email")
    public String email = null;

    @SerializedName("name")
    public String name = null;

    @SerializedName("reply_to_email")
    public String replyToEmail = null;

    @SerializedName("reply_to_name")
    public String replyToName = null;

    @SerializedName("add_note")
    public Boolean addNote = null;

    @SerializedName("personal_note")
    public String personalNote = null;


    /**
     * Resets all fields so the builder can be reused.
     */
    public void reset() {

        domainId = null;
        email = null;
        name = null;
        replyToEmail = null;
        replyToName = null;
        addNote = null;
        personalNote = null;
    }
}
