/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.whatsapp;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendApi;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.util.JsonSerializationDeserializationStrategy;

/**
 * <p>WhatsAppBuilder class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class WhatsAppBuilder {

    private WhatsAppBuilderBody builderBody;
    private MailerSend apiObjectReference;

    /**
     * <p>Constructor for WhatsAppBuilder.</p>
     *
     * @param ref a {@link com.mailersend.sdk.MailerSend} object.
     */
    public WhatsAppBuilder(MailerSend ref) {
        apiObjectReference = ref;
        builderBody = new WhatsAppBuilderBody();
    }

    /**
     * <p>from.</p>
     *
     * @param from the sender to send from, either a connected WhatsApp sender's phone number in E.164 format or its MailerSend sender ID
     * @return a {@link com.mailersend.sdk.whatsapp.WhatsAppBuilder} object.
     */
    public WhatsAppBuilder from(String from) {
        builderBody.from = from;
        return this;
    }

    /**
     * <p>addRecipient.</p>
     *
     * @param recipient a phone number in E.164 format, or a BSUID taken from an inbound message
     * @return a {@link com.mailersend.sdk.whatsapp.WhatsAppBuilder} object.
     */
    public WhatsAppBuilder addRecipient(String recipient) {
        builderBody.to.add(recipient);
        return this;
    }

    /**
     * <p>templateId.</p>
     *
     * @param templateId the ID of the approved WhatsApp template
     * @return a {@link com.mailersend.sdk.whatsapp.WhatsAppBuilder} object.
     */
    public WhatsAppBuilder templateId(String templateId) {
        builderBody.templateId = templateId;
        return this;
    }

    /**
     * <p>addPersonalization.</p>
     *
     * @param personalization a {@link com.mailersend.sdk.whatsapp.WhatsAppPersonalization} object.
     * @return a {@link com.mailersend.sdk.whatsapp.WhatsAppBuilder} object.
     */
    public WhatsAppBuilder addPersonalization(WhatsAppPersonalization personalization) {
        if (builderBody.personalization == null) {
            builderBody.personalization = new ArrayList<WhatsAppPersonalization>();
        }
        builderBody.personalization.add(personalization);
        return this;
    }

    /**
     * <p>send.</p>
     *
     * @return the ID of the created message
     * @throws com.mailersend.sdk.exceptions.MailerSendException if any.
     */
    public String send() throws MailerSendException {
        String endpoint = "/whatsapp/send";

        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());

        Gson gson = new GsonBuilder()
                .addSerializationExclusionStrategy(new JsonSerializationDeserializationStrategy(false))
                .addDeserializationExclusionStrategy(new JsonSerializationDeserializationStrategy(true))
                .create();

        String json = gson.toJson(builderBody);

        builderBody = new WhatsAppBuilderBody();

        WhatsAppSendResponse response = api.postRequest(endpoint, json, WhatsAppSendResponse.class);

        if (response.messageId != null && !response.messageId.isBlank()) {
            return response.messageId;
        }

        if (response.data != null) {
            return response.data.id;
        }

        return null;
    }
}
