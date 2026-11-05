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
 * Resource usage and limits of the account.
 */
public class AccountResources {

    @SerializedName("domains")
    public AccountResourceUsage domains;

    @SerializedName("users")
    public AccountResourceUsage users;

    @SerializedName("free_accountant_seat")
    public AccountResourceUsage freeAccountantSeat;

    @SerializedName("templates")
    public AccountResourceUsage templates;

    @SerializedName("smtp_users")
    public AccountResourceUsage smtpUsers;

    @SerializedName("api_tokens")
    public AccountResourceUsage apiTokens;

    @SerializedName("webhooks")
    public AccountResourceUsage webhooks;
}
