/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.smtpusers;

import java.util.ArrayList;
import java.util.stream.IntStream;

import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendApi;
import com.mailersend.sdk.MailerSendResponse;
import com.mailersend.sdk.exceptions.MailerSendException;

/**
 * <p>SmtpUsers class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class SmtpUsers {

    private MailerSend apiObjectReference;

    private int pageFilter = 1;
    private int limitFilter = 25;

    private SmtpUserBuilder smtpUserBuilder;


    /**
     * Do not initialize directly. This should only be accessed from MailerSend.smtpUsers
     *
     * @param ref a {@link com.mailersend.sdk.MailerSend} object.
     */
    public SmtpUsers(MailerSend ref) {

        apiObjectReference = ref;
        smtpUserBuilder = new SmtpUserBuilder(ref);
    }


    /**
     * Get the SMTP user builder
     *
     * @return a {@link com.mailersend.sdk.smtpusers.SmtpUserBuilder} object.
     */
    public SmtpUserBuilder smtpUserBuilder() {

        return smtpUserBuilder;
    }


    /**
     * Set the page of the request
     *
     * @param page a int.
     * @return a {@link com.mailersend.sdk.smtpusers.SmtpUsers} object.
     */
    public SmtpUsers page(int page) {

        pageFilter = page;

        return this;
    }


    /**
     * Set the results limit (10 - 100)
     *
     * @param limit a int.
     * @return a {@link com.mailersend.sdk.smtpusers.SmtpUsers} object.
     */
    public SmtpUsers limit(int limit) {

        limitFilter = limit;

        return this;
    }


    /**
     * Gets a list of SMTP users for the given domain
     *
     * @param domainId a {@link java.lang.String} object.
     * @throws com.mailersend.sdk.exceptions.MailerSendException
     * @return a {@link com.mailersend.sdk.smtpusers.SmtpUsersList} object.
     */
    public SmtpUsersList getSmtpUsers(String domainId) throws MailerSendException {

        String endpoint = "/domains/".concat(domainId).concat("/smtp-users").concat(prepareParamsUrl());

        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());

        SmtpUsersList response = api.getRequest(endpoint, SmtpUsersList.class);

        return response;
    }


    /**
     * Gets a single SMTP user
     *
     * @param domainId a {@link java.lang.String} object.
     * @param smtpUserId a {@link java.lang.String} object.
     * @throws com.mailersend.sdk.exceptions.MailerSendException
     * @return a {@link com.mailersend.sdk.smtpusers.SmtpUser} object.
     */
    public SmtpUser getSmtpUser(String domainId, String smtpUserId) throws MailerSendException {

        String endpoint = "/domains/".concat(domainId).concat("/smtp-users/").concat(smtpUserId);

        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());

        SingleSmtpUserResponse response = api.getRequest(endpoint, SingleSmtpUserResponse.class);

        return response.smtpUser;
    }


    /**
     * Deletes an SMTP user
     *
     * @param domainId a {@link java.lang.String} object.
     * @param smtpUserId a {@link java.lang.String} object.
     * @throws com.mailersend.sdk.exceptions.MailerSendException
     * @return a boolean.
     */
    public boolean deleteSmtpUser(String domainId, String smtpUserId) throws MailerSendException {

        String endpoint = "/domains/".concat(domainId).concat("/smtp-users/").concat(smtpUserId);

        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());

        MailerSendResponse response = api.deleteRequest(endpoint, MailerSendResponse.class);

        // return true if the response is 200, 204 or 202
        return IntStream.of(new int[] {200, 204, 202}).anyMatch(x -> x == response.responseStatusCode);
    }


    /**
     * Prepares the query part of the request url
     * @return
     */
    private String prepareParamsUrl() {

        ArrayList<String> params = new ArrayList<String>();

        params.add("page=".concat(String.valueOf(pageFilter)));
        params.add("limit=".concat(String.valueOf(limitFilter)));

        String requestParams = "";
        for (int i = 0; i < params.size(); i++) {

            String attrSep = "&";

            if (i == 0) {

                attrSep = "?";
            }

            requestParams = requestParams.concat(attrSep).concat(params.get(i));
        }

        return requestParams;
    }
}
