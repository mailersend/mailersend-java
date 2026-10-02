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

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

/**
 * <p>WhatsAppInboundMessage class.</p>
 *
 * Every inbound message has exactly one content field, determined by its type.
 * The other content fields are null.
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

    @SerializedName("text")
    public WhatsAppInboundMessageText text;

    /**
     * Present for image, audio, video, document and sticker messages
     */
    @SerializedName("attachment")
    public WhatsAppInboundMessageAttachment attachment;

    @SerializedName("location")
    public WhatsAppInboundMessageLocation location;

    /**
     * The shared contact cards, as sent by WhatsApp
     */
    @SerializedName("contacts")
    public JsonArray contacts;

    @SerializedName("reaction")
    public WhatsAppInboundMessageReaction reaction;

    @SerializedName("button")
    public WhatsAppInboundMessageButton button;

    /**
     * Present for interactive messages that are a list reply
     */
    @SerializedName("list_reply")
    public WhatsAppInboundMessageListReply listReply;

    /**
     * The raw interactive object, for interactive messages that are not a list reply
     */
    @SerializedName("interactive")
    public JsonObject interactive;

    /**
     * The raw payload as sent by WhatsApp
     */
    @SerializedName("order")
    public JsonElement order;

    /**
     * The raw payload as sent by WhatsApp
     */
    @SerializedName("system")
    public JsonElement system;

    /**
     * The raw payload as sent by WhatsApp
     */
    @SerializedName("unknown")
    public JsonElement unknown;

    /**
     * The raw payload as sent by WhatsApp
     */
    @SerializedName("unsupported")
    public JsonElement unsupported;

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
