/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.whatsapp.items;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

import com.google.gson.annotations.SerializedName;

/**
 * <p>The delivery of a WhatsApp message to one recipient.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class WhatsAppItem {

    @SerializedName("id")
    public String id;

    @SerializedName("whatsapp_message_id")
    public String whatsappMessageId;

    @SerializedName("whatsapp_account_id")
    public String whatsappAccountId;

    @SerializedName("from")
    public String from;

    @SerializedName("to")
    public String to;

    @SerializedName("status")
    public String status;

    @SerializedName("template_id")
    public String templateId;

    @SerializedName("template_name")
    public String templateName;

    @SerializedName("error_code")
    public Integer errorCode;

    @SerializedName("error_message")
    public String errorMessage;

    @SerializedName("created_at")
    private String createdAtStr;

    public Date createdAt;

    /**
     * Only returned when retrieving a single item
     */
    @SerializedName("recipient")
    public WhatsAppItemRecipient recipient;

    /**
     * Every status the item moved through. Only returned when retrieving a single item
     */
    @SerializedName("activity")
    public WhatsAppItemActivity[] activity;

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

        if (recipient != null) {
            recipient.postDeserialize();
        }

        if (activity != null) {
            for (WhatsAppItemActivity a : activity) {
                a.postDeserialize();
            }
        }
    }
}
