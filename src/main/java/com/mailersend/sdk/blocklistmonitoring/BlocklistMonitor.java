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
import com.mailersend.sdk.MailerSendResponse;

/**
 * Represents a Blocklist Monitor.
 */
public class BlocklistMonitor extends MailerSendResponse {

    @SerializedName("id")
    public String id;

    @SerializedName("name")
    public String name;

    @SerializedName("address")
    public String address;

    @SerializedName("type")
    public String type;

    @SerializedName("in_progress")
    public boolean inProgress;

    @SerializedName("notify")
    public boolean notify;

    @SerializedName("blocklisted")
    public boolean blocklisted;

    @SerializedName("last_checks_count")
    public int lastChecksCount;

    @SerializedName("last_check")
    private String lastCheckStr;

    @SerializedName("next_check")
    private String nextCheckStr;

    public Date lastCheck;
    public Date nextCheck;

    @SerializedName("has_children")
    public boolean hasChildren;

    @SerializedName("children_count")
    public Integer childrenCount;

    @SerializedName("forwards")
    public BlocklistMonitorForward[] forwards;

    /**
     * Present on single-monitor responses when has_children is false.
     */
    @SerializedName("hits")
    public BlocklistMonitorHit[] hits;

    /**
     * Present on single-monitor responses when has_children is true (IP range monitors).
     */
    @SerializedName("children")
    public BlocklistMonitor[] children;

    @SerializedName("created_at")
    private String createdAtStr;

    @SerializedName("updated_at")
    private String updatedAtStr;

    public Date createdAt;
    public Date updatedAt;

    /**
     * Called after deserialization to parse date strings into Date objects.
     */
    public void postDeserialize() {
        parseDates();
    }

    private void parseDates() {
        TemporalAccessor ta;
        Instant instant;

        if (createdAtStr != null && !createdAtStr.isBlank()) {
            ta = DateTimeFormatter.ISO_INSTANT.parse(createdAtStr);
            instant = Instant.from(ta);
            createdAt = Date.from(instant);
        }

        if (updatedAtStr != null && !updatedAtStr.isBlank()) {
            ta = DateTimeFormatter.ISO_INSTANT.parse(updatedAtStr);
            instant = Instant.from(ta);
            updatedAt = Date.from(instant);
        }

        if (lastCheckStr != null && !lastCheckStr.isBlank()) {
            ta = DateTimeFormatter.ISO_INSTANT.parse(lastCheckStr);
            instant = Instant.from(ta);
            lastCheck = Date.from(instant);
        }

        if (nextCheckStr != null && !nextCheckStr.isBlank()) {
            ta = DateTimeFormatter.ISO_INSTANT.parse(nextCheckStr);
            instant = Instant.from(ta);
            nextCheck = Date.from(instant);
        }
    }
}
