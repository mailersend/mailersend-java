/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.whatsapp.activities;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

import com.google.gson.annotations.SerializedName;

/**
 * <p>A single status event of a WhatsApp item.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class WhatsAppActivity {

    @SerializedName("id")
    public String id;

    @SerializedName("whatsapp_item_id")
    public String whatsappItemId;

    @SerializedName("whatsapp_message_id")
    public String whatsappMessageId;

    @SerializedName("whatsapp_account_id")
    public String whatsappAccountId;

    @SerializedName("from")
    public String from;

    @SerializedName("to")
    public String to;

    @SerializedName("type")
    public String type;

    @SerializedName("created_at")
    private String createdAtStr;

    public Date createdAt;

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
    }
}
