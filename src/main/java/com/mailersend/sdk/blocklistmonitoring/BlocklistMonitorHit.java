/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.blocklistmonitoring;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

import com.google.gson.annotations.SerializedName;

/**
 * Represents a single blocklist hit for a monitor.
 */
public class BlocklistMonitorHit {

    @SerializedName("blocklist_host")
    public String blocklistHost;

    @SerializedName("address")
    public String address;

    @SerializedName("listed_at")
    private String listedAtStr;

    @SerializedName("delisted_at")
    private String delistedAtStr;

    @SerializedName("last_checked_at")
    private String lastCheckedAtStr;

    public Date listedAt;
    public Date delistedAt;
    public Date lastCheckedAt;

    /**
     * Called after deserialization to parse date strings into Date objects.
     */
    public void postDeserialize() {
        parseDates();
    }

    private void parseDates() {
        TemporalAccessor ta;
        Instant instant;

        if (listedAtStr != null && !listedAtStr.isBlank()) {
            ta = DateTimeFormatter.ISO_INSTANT.parse(listedAtStr);
            instant = Instant.from(ta);
            listedAt = Date.from(instant);
        }

        if (delistedAtStr != null && !delistedAtStr.isBlank()) {
            ta = DateTimeFormatter.ISO_INSTANT.parse(delistedAtStr);
            instant = Instant.from(ta);
            delistedAt = Date.from(instant);
        }

        if (lastCheckedAtStr != null && !lastCheckedAtStr.isBlank()) {
            ta = DateTimeFormatter.ISO_INSTANT.parse(lastCheckedAtStr);
            instant = Instant.from(ta);
            lastCheckedAt = Date.from(instant);
        }
    }
}
