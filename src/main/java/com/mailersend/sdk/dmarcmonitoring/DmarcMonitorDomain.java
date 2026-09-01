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
 * Represents the domain object nested inside a DmarcMonitor response.
 */
public class DmarcMonitorDomain {

    @SerializedName("id")
    public String id;

    @SerializedName("name")
    public String name;
}
