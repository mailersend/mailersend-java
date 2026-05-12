/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.smtpusers;

import com.google.gson.annotations.SerializedName;

/**
 * <p>SmtpUserRequestBody class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class SmtpUserRequestBody {

    @SerializedName("name")
    public String name;

    @SerializedName("enabled")
    public Boolean enabled;

    /**
     * Resets the body values so that the builder can be reused
     */
    protected void reset() {
        name = null;
        enabled = null;
    }
}
