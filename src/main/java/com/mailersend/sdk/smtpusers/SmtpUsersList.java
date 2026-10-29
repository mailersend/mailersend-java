/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.smtpusers;

import com.google.gson.annotations.SerializedName;
import com.mailersend.sdk.MailerSendResponse;

/**
 * <p>SmtpUsersList class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class SmtpUsersList extends MailerSendResponse {

    @SerializedName("data")
    public SmtpUser[] smtpUsers;


    /**
     * Is called to perform any actions after the deserialization of the response
     * Do not call directly
     */
    public void postDeserialize() {

        if (smtpUsers == null) {

            return;
        }

        for (SmtpUser smtpUser : smtpUsers) {

            smtpUser.postDeserialize();
        }
    }
}
