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
 * <p>WhatsAppRecipient class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class WhatsAppRecipient {

    @SerializedName("id")
    public String id;

    @SerializedName("phone_number")
    public String phoneNumber;

    @SerializedName("username")
    public String username;

    @SerializedName("bsuid")
    public String bsuid;

    @SerializedName("status")
    public String status;

    @SerializedName("created_at")
    private String createdAtStr;

    public Date createdAt;

    /**
     * The recipient's latest 25 messages. Only returned when retrieving a single recipient
     */
    @SerializedName("messages")
    public WhatsAppRecipientMessage[] messages;

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

        if (messages != null) {
            for (WhatsAppRecipientMessage m : messages) {
                m.postDeserialize();
            }
        }
    }
}
