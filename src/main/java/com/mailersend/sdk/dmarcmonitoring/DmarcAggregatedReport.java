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
 * Represents a single row in an aggregated DMARC report.
 */
public class DmarcAggregatedReport extends MailerSendResponse {

    @SerializedName("ip_address")
    public String ipAddress;

    @SerializedName("total_volume")
    public int totalVolume;

    @SerializedName("passed_dmarc")
    public float passedDmarc;

    @SerializedName("passed_spf")
    public float passedSpf;

    @SerializedName("passed_dkim")
    public float passedDkim;

    @SerializedName("domain")
    public String domain;

    @SerializedName("country")
    public String country;

    @SerializedName("is_favorite")
    public boolean isFavorite;
}
