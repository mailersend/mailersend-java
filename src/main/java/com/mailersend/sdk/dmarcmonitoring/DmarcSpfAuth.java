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
 * Represents an SPF authentication result in a DMARC IP report.
 */
public class DmarcSpfAuth {

    @SerializedName("type")
    public String type;

    @SerializedName("domain")
    public String domain;

    @SerializedName("result")
    public String result;
}
