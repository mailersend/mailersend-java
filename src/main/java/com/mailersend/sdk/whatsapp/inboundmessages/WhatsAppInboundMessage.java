/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.whatsapp.inboundmessages;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

/**
 * <p>WhatsAppInboundMessage class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class WhatsAppInboundMessage {

    @SerializedName("id")
    public String id;

    @SerializedName("whatsapp_account_id")
    public String whatsappAccountId;

    @SerializedName("from")
    public String from;

    @SerializedName("to")
    public String to;

    @SerializedName("type")
    public String type;

    @SerializedName("received_at")
    private String receivedAtStr;

    public Date receivedAt;

    /**
     * Present only when the message is a reply
     */
    @SerializedName("context")
    public WhatsAppInboundMessageContext context;

    /**
     * The content of the message. Its shape depends on type, for example body for text messages,
     * or url, status, mime_type, size and caption for media messages. Null when there is no content
     */
    @SerializedName("meta")
    public JsonObject meta;

    /**
     * <p>postDeserialize.</p>
     */
    public void postDeserialize() {
        if (receivedAtStr != null && !receivedAtStr.isBlank()) {

            TemporalAccessor ta;
            Instant instant;

            ta = DateTimeFormatter.ISO_INSTANT.parse(receivedAtStr);
            instant = Instant.from(ta);
            receivedAt = Date.from(instant);
        }
    }
}
