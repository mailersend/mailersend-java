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
 * API rate limits of the account, in requests per minute.
 */
public class AccountRateLimits {

    @SerializedName("general")
    public int general;

    @SerializedName("email")
    public int email;

    @SerializedName("bulk_email")
    public int bulkEmail;

    @SerializedName("activity")
    public int activity;
}
