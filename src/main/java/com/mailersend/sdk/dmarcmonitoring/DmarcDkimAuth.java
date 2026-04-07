/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.dmarcmonitoring;

import com.google.gson.annotations.SerializedName;

/**
 * Represents a DKIM authentication result in a DMARC IP report.
 */
public class DmarcDkimAuth {

    @SerializedName("domain")
    public String domain;

    @SerializedName("selector")
    public String selector;

    @SerializedName("result")
    public String result;
}
