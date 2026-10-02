/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.whatsapp.recipients;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

import com.google.gson.annotations.SerializedName;

/**
 * <p>WhatsAppRecipientMessage class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class WhatsAppRecipientMessage {

    @SerializedName("id")
    public String id;

    @SerializedName("whatsapp_message_id")
    public String whatsappMessageId;

    @SerializedName("status")
    public String status;

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
