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
    public String passedDmarc;

    @SerializedName("passed_spf")
    public String passedSpf;

    @SerializedName("passed_dkim")
    public String passedDkim;

    @SerializedName("domain")
    public String domain;

    @SerializedName("country")
    public String country;

    @SerializedName("country_code")
    public String countryCode;

    @SerializedName("city")
    public String city;

    @SerializedName("is_favorite")
    public boolean isFavorite;
}
