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
 * Represents a MailerSend account user.
 */
public class User {

    @SerializedName("id")
    public String id;

    @SerializedName("avatar")
    public String avatar;

    @SerializedName("email")
    public String email;

    @SerializedName("last_name")
    public String lastName;

    @SerializedName("name")
    public String name;

    @SerializedName("2fa")
    public boolean twoFactorAuth;

    @SerializedName("role")
    public String role;

    @SerializedName("permissions")
    public String[] permissions;

    @SerializedName("domains")
    public UserDomain[] domains;

    @SerializedName("templates")
    public UserTemplate[] templates;

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

        if (domains != null) {
            for (UserDomain domain : domains) {
                domain.postDeserialize();
            }
        }

        if (templates != null) {
            for (UserTemplate template : templates) {
                template.postDeserialize();
            }
        }
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
