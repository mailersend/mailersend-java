/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.senderidentities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendApi;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.util.JsonSerializationDeserializationStrategy;

/**
 * Builder for creating and updating sender identities.
 *
 * @author mailersend
 */
public class SenderIdentityBuilder {

    private MailerSend apiObjectReference;

    private SenderIdentityBody builderBody;


    /**
     * No instantiation from outside the SDK.
     *
     * @param apiObjectRef a {@link com.mailersend.sdk.MailerSend} object.
     */
    protected SenderIdentityBuilder(MailerSend apiObjectRef) {

        apiObjectReference = apiObjectRef;
        builderBody = new SenderIdentityBody();
    }


    /**
     * Set the domain ID for the identity (required for create).
     *
     * @param domainId a {@link java.lang.String} object.
     * @return a {@link com.mailersend.sdk.senderidentities.SenderIdentityBuilder} object.
     */
    public SenderIdentityBuilder domainId(String domainId) {

        builderBody.domainId = domainId;

        return this;
    }


    /**
     * Set the email address for the identity (required for create).
     *
     * @param email a {@link java.lang.String} object.
     * @return a {@link com.mailersend.sdk.senderidentities.SenderIdentityBuilder} object.
     */
    public SenderIdentityBuilder email(String email) {

        builderBody.email = email;

        return this;
    }


    /**
     * Set the display name for the identity.
     *
     * @param name a {@link java.lang.String} object.
     * @return a {@link com.mailersend.sdk.senderidentities.SenderIdentityBuilder} object.
     */
    public SenderIdentityBuilder name(String name) {

        builderBody.name = name;

        return this;
    }


    /**
     * Set the reply-to email address.
     *
     * @param replyToEmail a {@link java.lang.String} object.
     * @return a {@link com.mailersend.sdk.senderidentities.SenderIdentityBuilder} object.
     */
    public SenderIdentityBuilder replyToEmail(String replyToEmail) {

        builderBody.replyToEmail = replyToEmail;

        return this;
    }


    /**
     * Set the reply-to display name.
     *
     * @param replyToName a {@link java.lang.String} object.
     * @return a {@link com.mailersend.sdk.senderidentities.SenderIdentityBuilder} object.
     */
    public SenderIdentityBuilder replyToName(String replyToName) {

        builderBody.replyToName = replyToName;

        return this;
    }


    /**
     * Set whether to add a personal note to the confirmation email.
     *
     * @param addNote a boolean.
     * @return a {@link com.mailersend.sdk.senderidentities.SenderIdentityBuilder} object.
     */
    public SenderIdentityBuilder addNote(boolean addNote) {

        builderBody.addNote = addNote;

        return this;
    }


    /**
     * Set the personal note text included in the confirmation email.
     *
     * @param personalNote a {@link java.lang.String} object.
     * @return a {@link com.mailersend.sdk.senderidentities.SenderIdentityBuilder} object.
     */
    public SenderIdentityBuilder personalNote(String personalNote) {

        builderBody.personalNote = personalNote;

        return this;
    }


    /**
     * Creates a new sender identity.
     *
     * @throws com.mailersend.sdk.exceptions.MailerSendException
     * @return a {@link com.mailersend.sdk.senderidentities.SenderIdentity} object.
     */
    public SenderIdentity createIdentity() throws MailerSendException {

        if (builderBody.domainId == null || builderBody.domainId.isBlank()) {

            throw new MailerSendException("Domain ID cannot be empty");
        }

        if (builderBody.email == null || builderBody.email.isBlank()) {

            throw new MailerSendException("Email cannot be empty");
        }

        String endpoint = "/identities";

        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());

        Gson gson = new GsonBuilder()
                .addSerializationExclusionStrategy(new JsonSerializationDeserializationStrategy(false))
                .addDeserializationExclusionStrategy(new JsonSerializationDeserializationStrategy(true))
                .create();

        String json = gson.toJson(builderBody);

        builderBody.reset();

        SingleSenderIdentityResponse response = api.postRequest(endpoint, json, SingleSenderIdentityResponse.class);

        response.identity.postDeserialize();

        return response.identity;
    }


    /**
     * Updates the sender identity with the given ID.
     *
     * @param identityId a {@link java.lang.String} object.
     * @throws com.mailersend.sdk.exceptions.MailerSendException
     * @return a {@link com.mailersend.sdk.senderidentities.SenderIdentity} object.
     */
    public SenderIdentity updateIdentity(String identityId) throws MailerSendException {

        if (identityId == null || identityId.isBlank()) {

            throw new MailerSendException("Identity ID cannot be empty");
        }

        String endpoint = "/identities/".concat(identityId);

        return performUpdate(endpoint);
    }


    /**
     * Updates the sender identity with the given email address.
     *
     * @param email a {@link java.lang.String} object.
     * @throws com.mailersend.sdk.exceptions.MailerSendException
     * @return a {@link com.mailersend.sdk.senderidentities.SenderIdentity} object.
     */
    public SenderIdentity updateIdentityByEmail(String email) throws MailerSendException {

        if (email == null || email.isBlank()) {

            throw new MailerSendException("Email cannot be empty");
        }

        String endpoint = "/identities/email/".concat(email);

        return performUpdate(endpoint);
    }


    /**
     * Shared PUT logic for update methods.
     */
    private SenderIdentity performUpdate(String endpoint) throws MailerSendException {

        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());

        Gson gson = new GsonBuilder()
                .addSerializationExclusionStrategy(new JsonSerializationDeserializationStrategy(false))
                .addDeserializationExclusionStrategy(new JsonSerializationDeserializationStrategy(true))
                .create();

        String json = gson.toJson(builderBody);

        builderBody.reset();

        SingleSenderIdentityResponse response = api.putRequest(endpoint, json, SingleSenderIdentityResponse.class);

        response.identity.postDeserialize();

        return response.identity;
    }
}
