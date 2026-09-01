/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.senderidentities;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

import com.google.gson.annotations.SerializedName;

/**
 * Represents the domain object embedded in a sender identity response.
 */
public class SenderIdentityDomain {

    @SerializedName("id")
    public String id;

    @SerializedName("name")
    public String name;

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
    }
}
