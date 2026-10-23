/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.templates;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

import com.google.gson.annotations.SerializedName;

/**
 * <p>TemplateTranslation class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class TemplateTranslation {

    @SerializedName("language")
    public String language;

    @SerializedName("language_name")
    public String languageName;

    @SerializedName("status")
    public String status;

    @SerializedName("total_email_sent")
    public int totalEmailSent;

    public Date lastEmailSentAt;

    @SerializedName("last_email_sent_at")
    private String lastEmailSentAtStr;

    public Date translatedAt;

    @SerializedName("translated_at")
    private String translatedAtStr;

    public Date createdAt;

    @SerializedName("created_at")
    private String createdAtStr;

    public Date updatedAt;

    @SerializedName("updated_at")
    private String updatedAtStr;


    /**
     * Is called to perform any actions after the deserialization of the response
     * Do not call directly
     */
    protected void postDeserialize() {

        lastEmailSentAt = parseDate(lastEmailSentAtStr);
        translatedAt = parseDate(translatedAtStr);
        createdAt = parseDate(createdAtStr);
        updatedAt = parseDate(updatedAtStr);
    }


    /**
     * Parses a string date from the response into a java.util.Date object
     */
    private Date parseDate(String date) {

        if (date == null || date.isBlank()) {

            return null;
        }

        TemporalAccessor ta = DateTimeFormatter.ISO_INSTANT.parse(date);
        Instant instant = Instant.from(ta);

        return Date.from(instant);
    }
}
