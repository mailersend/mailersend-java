/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.senderidentities;

import java.util.ArrayList;
import java.util.stream.IntStream;

import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendApi;
import com.mailersend.sdk.MailerSendResponse;
import com.mailersend.sdk.exceptions.MailerSendException;

/**
 * Provides access to the Sender Identities API endpoints.
 *
 * <p>Do not initialize directly — access via {@code MailerSend.senderIdentities()}.</p>
 *
 * @author mailersend
 */
public class SenderIdentities {

    private MailerSend apiObjectReference;

    private SenderIdentityBuilder identityBuilder;

    private int pageFilter = 1;
    private int limitFilter = 25;
    private String domainIdFilter = null;
    private String queryFilter = null;
    private String orderByFilter = null;
    private String orderFilter = null;


    /**
     * Do not initialize directly. This should only be accessed from MailerSend.senderIdentities().
     *
     * @param ref a {@link com.mailersend.sdk.MailerSend} object.
     */
    public SenderIdentities(MailerSend ref) {

        apiObjectReference = ref;
        identityBuilder = new SenderIdentityBuilder(ref);
    }


    /**
     * Returns the builder used to create or update sender identities.
     *
     * @return a {@link com.mailersend.sdk.senderidentities.SenderIdentityBuilder} object.
     */
    public SenderIdentityBuilder builder() {

        return identityBuilder;
    }


    /**
     * Set the page for list requests.
     *
     * @param page a int.
     * @return a {@link com.mailersend.sdk.senderidentities.SenderIdentities} object.
     */
    public SenderIdentities page(int page) {

        pageFilter = page;

        return this;
    }


    /**
     * Set the results limit for list requests (10–100).
     *
     * @param limit a int.
     * @return a {@link com.mailersend.sdk.senderidentities.SenderIdentities} object.
     */
    public SenderIdentities limit(int limit) {

        limitFilter = limit;

        return this;
    }


    /**
     * Filter identities by domain ID.
     *
     * @param domainId a {@link java.lang.String} object.
     * @return a {@link com.mailersend.sdk.senderidentities.SenderIdentities} object.
     */
    public SenderIdentities domainId(String domainId) {

        domainIdFilter = domainId;

        return this;
    }


    /**
     * Filter identities by email address (partial match).
     *
     * @param query a {@link java.lang.String} object.
     * @return a {@link com.mailersend.sdk.senderidentities.SenderIdentities} object.
     */
    public SenderIdentities query(String query) {

        queryFilter = query;

        return this;
    }


    /**
     * Set the field to order list results by.
     * Valid values: {@code email}, {@code created_at}, {@code verified_at}.
     *
     * @param orderBy a {@link java.lang.String} object.
     * @return a {@link com.mailersend.sdk.senderidentities.SenderIdentities} object.
     */
    public SenderIdentities orderBy(String orderBy) {

        orderByFilter = orderBy;

        return this;
    }


    /**
     * Set the sort direction for list results.
     * Valid values: {@code asc}, {@code desc}.
     *
     * @param order a {@link java.lang.String} object.
     * @return a {@link com.mailersend.sdk.senderidentities.SenderIdentities} object.
     */
    public SenderIdentities order(String order) {

        orderFilter = order;

        return this;
    }


    /**
     * Retrieves a paginated list of sender identities.
     *
     * @throws com.mailersend.sdk.exceptions.MailerSendException
     * @return a {@link com.mailersend.sdk.senderidentities.SenderIdentitiesList} object.
     */
    public SenderIdentitiesList getIdentities() throws MailerSendException {

        String endpoint = "/identities".concat(prepareParamsUrl());

        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());

        SenderIdentitiesList response = api.getRequest(endpoint, SenderIdentitiesList.class);

        if (response.identities != null) {
            for (SenderIdentity identity : response.identities) {
                identity.postDeserialize();
            }
        }

        return response;
    }


    /**
     * Retrieves a single sender identity by its ID.
     *
     * @param identityId a {@link java.lang.String} object.
     * @throws com.mailersend.sdk.exceptions.MailerSendException
     * @return a {@link com.mailersend.sdk.senderidentities.SenderIdentity} object.
     */
    public SenderIdentity getIdentity(String identityId) throws MailerSendException {

        String endpoint = "/identities/".concat(identityId);

        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());

        SingleSenderIdentityResponse response = api.getRequest(endpoint, SingleSenderIdentityResponse.class);

        response.identity.postDeserialize();

        return response.identity;
    }


    /**
     * Retrieves a single sender identity by its email address.
     *
     * @param email a {@link java.lang.String} object.
     * @throws com.mailersend.sdk.exceptions.MailerSendException
     * @return a {@link com.mailersend.sdk.senderidentities.SenderIdentity} object.
     */
    public SenderIdentity getIdentityByEmail(String email) throws MailerSendException {

        String endpoint = "/identities/email/".concat(email);

        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());

        SingleSenderIdentityResponse response = api.getRequest(endpoint, SingleSenderIdentityResponse.class);

        response.identity.postDeserialize();

        return response.identity;
    }


    /**
     * Deletes the sender identity with the given ID.
     *
     * @param identityId a {@link java.lang.String} object.
     * @throws com.mailersend.sdk.exceptions.MailerSendException
     * @return {@code true} if the deletion was accepted (HTTP 200, 202, or 204).
     */
    public boolean deleteIdentity(String identityId) throws MailerSendException {

        String endpoint = "/identities/".concat(identityId);

        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());

        MailerSendResponse response = api.deleteRequest(endpoint, MailerSendResponse.class);

        return IntStream.of(new int[]{200, 202, 204}).anyMatch(x -> x == response.responseStatusCode);
    }


    /**
     * Deletes the sender identity with the given email address.
     *
     * @param email a {@link java.lang.String} object.
     * @throws com.mailersend.sdk.exceptions.MailerSendException
     * @return {@code true} if the deletion was accepted (HTTP 200, 202, or 204).
     */
    public boolean deleteIdentityByEmail(String email) throws MailerSendException {

        String endpoint = "/identities/email/".concat(email);

        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());

        MailerSendResponse response = api.deleteRequest(endpoint, MailerSendResponse.class);

        return IntStream.of(new int[]{200, 202, 204}).anyMatch(x -> x == response.responseStatusCode);
    }


    /**
     * Resends the confirmation email for the sender identity with the given ID.
     *
     * @param identityId a {@link java.lang.String} object.
     * @throws com.mailersend.sdk.exceptions.MailerSendException
     * @return a {@link com.mailersend.sdk.senderidentities.SenderIdentity} object.
     */
    public SenderIdentity resendConfirmation(String identityId) throws MailerSendException {

        String endpoint = "/identities/".concat(identityId).concat("/resend");

        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());

        SingleSenderIdentityResponse response = api.postRequest(endpoint, "", SingleSenderIdentityResponse.class);

        response.identity.postDeserialize();

        return response.identity;
    }


    /**
     * Builds the query-string portion of the list endpoint URL.
     */
    private String prepareParamsUrl() {

        ArrayList<String> params = new ArrayList<String>();

        params.add("page=".concat(String.valueOf(pageFilter)));
        params.add("limit=".concat(String.valueOf(limitFilter)));

        if (domainIdFilter != null && !domainIdFilter.isBlank()) {
            params.add("domain_id=".concat(domainIdFilter));
        }

        if (queryFilter != null && !queryFilter.isBlank()) {
            params.add("query=".concat(queryFilter));
        }

        if (orderByFilter != null && !orderByFilter.isBlank()) {
            params.add("order_by=".concat(orderByFilter));
        }

        if (orderFilter != null && !orderFilter.isBlank()) {
            params.add("order=".concat(orderFilter));
        }

        String requestParams = "";
        for (int i = 0; i < params.size(); i++) {

            String attrSep = i == 0 ? "?" : "&";
            requestParams = requestParams.concat(attrSep).concat(params.get(i));
        }

        return requestParams;
    }
}
