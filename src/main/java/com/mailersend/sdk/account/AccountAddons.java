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
 * Add-ons of the account.
 */
public class AccountAddons {

    @SerializedName("sms")
    public AccountUsage sms;

    /**
     * {@code null} when the account has no WhatsApp add-on.
     */
    @SerializedName("whatsapp")
    public AccountWhatsAppUsage whatsapp;

    @SerializedName("phone_numbers")
    public int phoneNumbers;

    @SerializedName("domain_spaces")
    public int domainSpaces;

    @SerializedName("data_retention_days")
    public int dataRetentionDays;

    @SerializedName("dedicated_ips")
    public int dedicatedIps;
}
