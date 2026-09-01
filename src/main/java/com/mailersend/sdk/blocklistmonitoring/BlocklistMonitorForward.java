/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.blocklistmonitoring;

import com.google.gson.annotations.SerializedName;

/**
 * Represents a notification forward (email or webhook) attached to a blocklist monitor.
 */
public class BlocklistMonitorForward {

    @SerializedName("id")
    public String id;

    @SerializedName("type")
    public String type;

    @SerializedName("value")
    public String value;
}
