/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.whatsapp.messages;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

import com.google.gson.annotations.SerializedName;

/**
 * <p>WhatsAppMessage class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class WhatsAppMessage {

    @SerializedName("id")
    public String id;

    @SerializedName("from")
    public String from;

    @SerializedName("to")
    public String[] to;

    @SerializedName("whatsapp_account_id")
    public String whatsappAccountId;

    @SerializedName("template_id")
    public String templateId;

    @SerializedName("created_at")
    private String createdAtStr;

    public Date createdAt;

    /**
     * Only returned when retrieving a single message
     */
    @SerializedName("recipients")
    public WhatsAppMessageRecipient[] recipients;

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

        if (recipients != null) {
            for (WhatsAppMessageRecipient r : recipients) {
                r.postDeserialize();
            }
        }
    }
}
