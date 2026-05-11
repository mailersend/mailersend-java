/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.tokens;

import java.util.ArrayList;

import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendApi;
import com.mailersend.sdk.MailerSendResponse;
import com.mailersend.sdk.exceptions.MailerSendException;

/**
 * <p>Tokens class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class Tokens {

    private MailerSend apiObjectReference;

    private TokenAddBuilder addTokenBuilder;

    private int pageFilter = 1;
    private int limitFilter = 25;

    /**
     * <p>Constructor for Tokens.</p>
     *
     * @param ref a {@link com.mailersend.sdk.MailerSend} object.
     */
    public Tokens(MailerSend ref) {

        apiObjectReference = ref;

        addTokenBuilder = new TokenAddBuilder(ref);
    }


    /**
     * Returns the add token builder
     *
     * @return a {@link com.mailersend.sdk.tokens.TokenAddBuilder} object.
     */
    public TokenAddBuilder addBuilder() {

        return addTokenBuilder;
    }


    /**
     * Set the page for list requests
     *
     * @param page a int.
     * @return a {@link com.mailersend.sdk.tokens.Tokens} object.
     */
    public Tokens page(int page) {

        pageFilter = page;

        return this;
    }


    /**
     * Set the results limit for list requests (10 - 100)
     *
     * @param limit a int.
     * @return a {@link com.mailersend.sdk.tokens.Tokens} object.
     */
    public Tokens limit(int limit) {

        limitFilter = limit;

        return this;
    }


    /**
     * Retrieves a list of API tokens
     *
     * @throws com.mailersend.sdk.exceptions.MailerSendException
     * @return a {@link com.mailersend.sdk.tokens.TokenListResponse} object.
     */
    public TokenListResponse getTokens() throws MailerSendException {

        String endpoint = "/token".concat(prepareParamsUrl());

        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());

        TokenListResponse response = api.getRequest(endpoint, TokenListResponse.class);

        for (Token token : response.tokens) {
            token.postDeserialize();
        }

        return response;
    }


    /**
     * Retrieves a single API token by its ID
     *
     * @param tokenId a {@link java.lang.String} object.
     * @throws com.mailersend.sdk.exceptions.MailerSendException
     * @return a {@link com.mailersend.sdk.tokens.Token} object.
     */
    public Token getToken(String tokenId) throws MailerSendException {

        String endpoint = "/token/".concat(tokenId);

        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());

        TokenResponse response = api.getRequest(endpoint, TokenResponse.class);

        response.token.postDeserialize();

        return response.token;
    }


    /**
     * Updates a token's status
     *
     * @param tokenId a {@link java.lang.String} object.
     * @param paused a boolean.
     * @throws com.mailersend.sdk.exceptions.MailerSendException
     * @return a {@link com.mailersend.sdk.tokens.Token} object.
     */
    public Token updateToken(String tokenId, boolean paused) throws MailerSendException {

        String endpoint = "/token/".concat(tokenId);

        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());

        String status = paused ? "pause" : "unpause";
        String json = "{\"status\":\"" + status + "\"}";

        TokenResponse response = api.putRequest(endpoint, json, TokenResponse.class);

        response.token.postDeserialize();

        return response.token;
    }


    /**
     * Updates a token's name and/or status
     *
     * @param tokenId a {@link java.lang.String} object.
     * @param name a {@link java.lang.String} object, or null to leave unchanged.
     * @param status a {@link java.lang.String} object ("pause" or "unpause"), or null to leave unchanged.
     * @throws com.mailersend.sdk.exceptions.MailerSendException
     * @return a {@link com.mailersend.sdk.tokens.Token} object.
     */
    public Token updateToken(String tokenId, String name, String status) throws MailerSendException {

        boolean nameBlank = (name == null || name.isBlank());
        boolean statusBlank = (status == null || status.isBlank());

        if (nameBlank && statusBlank) {

            throw new MailerSendException("At least one of name or status must be provided");
        }

        String endpoint = "/token/".concat(tokenId);

        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());

        ArrayList<String> fields = new ArrayList<String>();

        if (name != null && !name.isBlank()) {
            fields.add("\"name\":\"" + name + "\"");
        }

        if (status != null && !status.isBlank()) {
            fields.add("\"status\":\"" + status + "\"");
        }

        String json = "{" + String.join(",", fields) + "}";

        TokenResponse response = api.putRequest(endpoint, json, TokenResponse.class);

        response.token.postDeserialize();

        return response.token;
    }


    /**
     * Deletes a token
     *
     * @param tokenId a {@link java.lang.String} object.
     * @throws com.mailersend.sdk.exceptions.MailerSendException
     * @return a {@link com.mailersend.sdk.MailerSendResponse} object.
     */
    public MailerSendResponse deleteToken(String tokenId) throws MailerSendException {

        String endpoint = "/token/".concat(tokenId);

        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());

        MailerSendResponse response = api.deleteRequest(endpoint, MailerSendResponse.class);

        return response;
    }


    /**
     * Prepares the query part of the request url
     *
     * @return a {@link java.lang.String} object.
     */
    private String prepareParamsUrl() {

        ArrayList<String> params = new ArrayList<String>();

        params.add("page=".concat(String.valueOf(pageFilter)));
        params.add("limit=".concat(String.valueOf(limitFilter)));

        String requestParams = "";
        for (int i = 0; i < params.size(); i++) {

            String attrSep = i == 0 ? "?" : "&";
            requestParams = requestParams.concat(attrSep).concat(params.get(i));
        }

        return requestParams;
    }

}
