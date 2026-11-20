/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.account;

import com.google.gson.annotations.SerializedName;

/**
 * Sent and received messages in the current usage cycle, against the limit in force.
 */
public class AccountUsage {

    @SerializedName("sent")
    public int sent;

    @SerializedName("received")
    public int received;

    @SerializedName("limit")
    public int limit;
}
