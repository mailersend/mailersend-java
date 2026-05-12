/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.users;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

import com.google.gson.annotations.SerializedName;

/**
 * Represents a pending user invite.
 */
public class Invite {

    @SerializedName("id")
    public String id;

    @SerializedName("email")
    public String email;

    @SerializedName("data")
    public InviteData data;

    @SerializedName("role")
    public String role;

    @SerializedName("permissions")
    public String[] permissions;

    @SerializedName("requires_periodic_password_change")
    public boolean requiresPeriodicPasswordChange;

    public Date createdAt;

    @SerializedName("created_at")
    private String createdAtStr;

    public Date updatedAt;

    @SerializedName("updated_at")
    private String updatedAtStr;


    /**
     * Is called to perform any actions after the deserialization of the response.
     * Do not call directly.
     */
    protected void postDeserialize() {

        parseDates();
    }


    /**
     * Parses the string dates from the response into java.util.Date objects.
     */
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
