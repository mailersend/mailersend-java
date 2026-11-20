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
 * Represents the account the API token belongs to.
 */
public class AccountDetails {

    @SerializedName("name")
    public String name;

    /**
     * One of {@code pending}, {@code approved}, {@code rejected} or {@code suspended}.
     */
    @SerializedName("status")
    public String status;

    @SerializedName("plan_type")
    public String planType;

    @SerializedName("data_retention_days")
    public int dataRetentionDays;

    @SerializedName("emails")
    public AccountUsage emails;

    @SerializedName("rate_limits")
    public AccountRateLimits rateLimits;

    @SerializedName("resources")
    public AccountResources resources;

    @SerializedName("addons")
    public AccountAddons addons;
}
