/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.dmarcmonitoring;

import com.google.gson.annotations.SerializedName;
import com.mailersend.sdk.MailerSendResponse;

/**
 * Represents a detailed per-IP DMARC report entry.
 */
public class DmarcIpReport extends MailerSendResponse {

    @SerializedName("id")
    public String id;

    @SerializedName("ip_address")
    public String ipAddress;

    @SerializedName("ip_domain")
    public String ipDomain;

    @SerializedName("total_volume")
    public int totalVolume;

    @SerializedName("passed_dmarc")
    public String passedDmarc;

    @SerializedName("passed_spf")
    public String passedSpf;

    @SerializedName("spf_domain")
    public String spfDomain;

    @SerializedName("aligned_spf")
    public boolean alignedSpf;

    @SerializedName("passed_dkim")
    public String passedDkim;

    @SerializedName("aligned_dkim")
    public boolean alignedDkim;

    @SerializedName("dkim_domain")
    public String dkimDomain;

    @SerializedName("applied_policy")
    public String appliedPolicy;

    @SerializedName("override_reason")
    public String overrideReason;

    @SerializedName("override_comment")
    public String overrideComment;

    @SerializedName("domain")
    public String domain;

    @SerializedName("country")
    public String country;

    @SerializedName("country_code")
    public String countryCode;

    @SerializedName("city")
    public String city;

    @SerializedName("report_source")
    public String reportSource;

    @SerializedName("spf_auth")
    public DmarcSpfAuth[] spfAuth;

    @SerializedName("dkim_auth")
    public DmarcDkimAuth[] dkimAuth;
}
