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
    public float passedDmarc;

    @SerializedName("passed_spf")
    public float passedSpf;

    @SerializedName("passed_dkim")
    public float passedDkim;

    @SerializedName("aligned_spf")
    public float alignedSpf;

    @SerializedName("aligned_dkim")
    public float alignedDkim;

    @SerializedName("applied_policy")
    public String appliedPolicy;

    @SerializedName("override_reason")
    public String overrideReason;

    @SerializedName("country")
    public String country;

    @SerializedName("report_source")
    public String reportSource;

    @SerializedName("spf_auth")
    public DmarcSpfAuth[] spfAuth;

    @SerializedName("dkim_auth")
    public DmarcDkimAuth[] dkimAuth;
}
