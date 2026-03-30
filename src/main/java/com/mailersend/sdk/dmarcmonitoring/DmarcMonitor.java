/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.dmarcmonitoring;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

import com.google.gson.annotations.SerializedName;
import com.mailersend.sdk.MailerSendResponse;

/**
 * Represents a DMARC monitor.
 */
public class DmarcMonitor extends MailerSendResponse {

    @SerializedName("id")
    public String id;

    @SerializedName("dmarc_record")
    public String dmarcRecord;

    @SerializedName("wanted_dmarc_record")
    public String wantedDmarcRecord;

    @SerializedName("dmarc_valid")
    public boolean dmarcValid;

    @SerializedName("dmarc_record_checked_at")
    private String dmarcRecordCheckedAtStr;

    public Date dmarcRecordCheckedAt;

    @SerializedName("spf_record")
    public String spfRecord;

    @SerializedName("spf_status")
    public String spfStatus;

    @SerializedName("domain")
    public DmarcMonitorDomain domain;

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

        if (dmarcRecordCheckedAtStr != null && !dmarcRecordCheckedAtStr.isBlank()) {
            ta = DateTimeFormatter.ISO_INSTANT.parse(dmarcRecordCheckedAtStr);
            instant = Instant.from(ta);
            dmarcRecordCheckedAt = Date.from(instant);
        }
    }
}
