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
 * How many of a resource the account uses, against how many its plan allows.
 * The limit can be {@code null}.
 */
public class AccountResourceUsage {

    @SerializedName("used")
    public int used;

    @SerializedName("limit")
    public Integer limit;
}
