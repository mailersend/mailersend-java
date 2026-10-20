/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.whatsapp.templates;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

import com.google.gson.annotations.SerializedName;

/**
 * <p>WhatsAppTemplate class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class WhatsAppTemplate {

    @SerializedName("id")
    public String id;

    @SerializedName("whatsapp_account_id")
    public String whatsappAccountId;

    /**
     * The template name registered with Meta
     */
    @SerializedName("name")
    public String name;

    /**
     * The name of the template shown in your MailerSend account
     */
    @SerializedName("readable_name")
    public String readableName;

    @SerializedName("category")
    public String category;

    @SerializedName("language")
    public String language;

    @SerializedName("status")
    public String status;

    @SerializedName("components")
    public WhatsAppTemplateComponents components;

    @SerializedName("created_at")
    private String createdAtStr;

    public Date createdAt;

    @SerializedName("updated_at")
    private String updatedAtStr;

    public Date updatedAt;

    /**
     * <p>postDeserialize.</p>
     */
    public void postDeserialize() {
        if (createdAtStr != null && !createdAtStr.isBlank()) {

            TemporalAccessor ta;
            Instant instant;

            ta = DateTimeFormatter.ISO_INSTANT.parse(createdAtStr);
            instant = Instant.from(ta);
            createdAt = Date.from(instant);
        }

        if (updatedAtStr != null && !updatedAtStr.isBlank()) {

            TemporalAccessor ta;
            Instant instant;

            ta = DateTimeFormatter.ISO_INSTANT.parse(updatedAtStr);
            instant = Instant.from(ta);
            updatedAt = Date.from(instant);
        }
    }
}
