/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.smtpusers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendApi;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.util.JsonSerializationDeserializationStrategy;

/**
 * <p>SmtpUserBuilder class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class SmtpUserBuilder {

    private MailerSend apiObjectReference;

    private SmtpUserRequestBody body;


    /**
     * No instantiation from outside the SDK
     *
     * @param apiObjectRef a {@link com.mailersend.sdk.MailerSend} object.
     */
    protected SmtpUserBuilder(MailerSend apiObjectRef) {

        apiObjectReference = apiObjectRef;
        body = new SmtpUserRequestBody();
    }


    /**
     * Set the name of the SMTP user
     *
     * @param name a {@link java.lang.String} object.
     * @return a {@link com.mailersend.sdk.smtpusers.SmtpUserBuilder} object.
     */
    public SmtpUserBuilder name(String name) {

        body.name = name;

        return this;
    }


    /**
     * Set whether the SMTP user is enabled
     *
     * @param enabled a boolean.
     * @return a {@link com.mailersend.sdk.smtpusers.SmtpUserBuilder} object.
     */
    public SmtpUserBuilder enabled(boolean enabled) {

        body.enabled = enabled;

        return this;
    }


    /**
     * Creates a new SMTP user for the given domain
     *
     * @param domainId a {@link java.lang.String} object.
     * @throws com.mailersend.sdk.exceptions.MailerSendException
     * @return a {@link com.mailersend.sdk.smtpusers.SmtpUser} object.
     */
    public SmtpUser createSmtpUser(String domainId) throws MailerSendException {

        String endpoint = "/domains/".concat(domainId).concat("/smtp-users");

        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());

        Gson gson = new GsonBuilder()
                .addSerializationExclusionStrategy(new JsonSerializationDeserializationStrategy(false))
                .addDeserializationExclusionStrategy(new JsonSerializationDeserializationStrategy(true))
                .create();

        String json = gson.toJson(body);

        // reset the body object's values so that it can be reused
        body.reset();

        SingleSmtpUserResponse response = api.postRequest(endpoint, json, SingleSmtpUserResponse.class);

        return response.smtpUser;
    }


    /**
     * Updates an existing SMTP user
     *
     * @param domainId a {@link java.lang.String} object.
     * @param smtpUserId a {@link java.lang.String} object.
     * @throws com.mailersend.sdk.exceptions.MailerSendException
     * @return a {@link com.mailersend.sdk.smtpusers.SmtpUser} object.
     */
    public SmtpUser updateSmtpUser(String domainId, String smtpUserId) throws MailerSendException {

        String endpoint = "/domains/".concat(domainId).concat("/smtp-users/").concat(smtpUserId);

        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());

        Gson gson = new GsonBuilder()
                .addSerializationExclusionStrategy(new JsonSerializationDeserializationStrategy(false))
                .addDeserializationExclusionStrategy(new JsonSerializationDeserializationStrategy(true))
                .create();

        String json = gson.toJson(body);

        // reset the body object's values so that it can be reused
        body.reset();

        SingleSmtpUserResponse response = api.putRequest(endpoint, json, SingleSmtpUserResponse.class);

        return response.smtpUser;
    }
}
