/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.emailverification;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendApi;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.util.JsonSerializationDeserializationStrategy;

/**
 * <p>EmailVerification class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class EmailVerification {
    private MailerSend apiObjectReference;
    
    private int pageFilter = 1;
    private int limitFilter = 25;

    // getList() params
    private Boolean detailedFilter = null;
    private int getListPageFilter = 1;
    private int getListLimitFilter = 25;

    // verificationResults() params
    private int resultsPageFilter = 1;
    private int resultsLimitFilter = 25;
    private String[] resultsFilter = null;

    private EmailVerificationBuilder builder;
    
    /**
     * <p>Constructor for EmailVerification.</p>
     *
     * @param ref a {@link com.mailersend.sdk.MailerSend} object.
     */
    public EmailVerification(MailerSend ref) {
    	apiObjectReference = ref;
    	builder = new EmailVerificationBuilder(ref);
    }
    
    /**
     * <p>page.</p>
     *
     * @param page a int.
     * @return a {@link com.mailersend.sdk.emailverification.EmailVerification} object.
     */
    public EmailVerification page(int page) {
    	pageFilter = page;
    	return this;
    }
    
    /**
     * <p>limit.</p>
     *
     * @param limit a int.
     * @return a {@link com.mailersend.sdk.emailverification.EmailVerification} object.
     */
    public EmailVerification limit(int limit) {
    	limitFilter = limit;
    	return this;
    }
    
    /**
     * When set to true, the getList response will include an emails object with paginated results.
     *
     * @param detailed a boolean.
     * @return a {@link com.mailersend.sdk.emailverification.EmailVerification} object.
     */
    public EmailVerification detailed(boolean detailed) {
        detailedFilter = detailed;
        return this;
    }

    /**
     * Page for getList (only relevant when detailed=true).
     *
     * @param page a int.
     * @return a {@link com.mailersend.sdk.emailverification.EmailVerification} object.
     */
    public EmailVerification getListPage(int page) {
        getListPageFilter = page;
        return this;
    }

    /**
     * Limit for getList (only relevant when detailed=true).
     *
     * @param limit a int.
     * @return a {@link com.mailersend.sdk.emailverification.EmailVerification} object.
     */
    public EmailVerification getListLimit(int limit) {
        getListLimitFilter = limit;
        return this;
    }

    /**
     * Page for verificationResults.
     *
     * @param page a int.
     * @return a {@link com.mailersend.sdk.emailverification.EmailVerification} object.
     */
    public EmailVerification resultsPage(int page) {
        resultsPageFilter = page;
        return this;
    }

    /**
     * Limit for verificationResults.
     *
     * @param limit a int.
     * @return a {@link com.mailersend.sdk.emailverification.EmailVerification} object.
     */
    public EmailVerification resultsLimit(int limit) {
        resultsLimitFilter = limit;
        return this;
    }

    /**
     * Filter verificationResults by result type(s), e.g. "valid", "catch_all".
     *
     * @param results an array of result type strings.
     * @return a {@link com.mailersend.sdk.emailverification.EmailVerification} object.
     */
    public EmailVerification results(String[] results) {
        resultsFilter = results;
        return this;
    }

    /**
     * <p>builder.</p>
     *
     * @return a {@link com.mailersend.sdk.emailverification.EmailVerificationBuilder} object.
     */
    public EmailVerificationBuilder builder() {
    	return builder;
    }
    
    /**
     * <p>newBuilder.</p>
     *
     * @return a {@link com.mailersend.sdk.emailverification.EmailVerificationBuilder} object.
     */
    public EmailVerificationBuilder newBuilder() {
    	return new EmailVerificationBuilder(apiObjectReference);
    }
    
    /**
     * <p>getLists.</p>
     *
     * @return a {@link com.mailersend.sdk.emailverification.EmailVerificationLists} object.
     * @throws com.mailersend.sdk.exceptions.MailerSendException if any.
     */
    public EmailVerificationLists getLists() throws MailerSendException {
    	String endpoint = "/email-verification".concat(prepareParamsUrl());
    	
        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());
        
        EmailVerificationLists lists = api.getRequest(endpoint, EmailVerificationLists.class);
        
        for (EmailVerificationList l : lists.lists) {
        	l.postDeserialize();
        }
        
        return lists;
    }
    
    /**
     * <p>getList.</p>
     *
     * @param emailVerificationId a {@link java.lang.String} object.
     * @return a {@link com.mailersend.sdk.emailverification.EmailVerificationList} object.
     * @throws com.mailersend.sdk.exceptions.MailerSendException if any.
     */
    public EmailVerificationList getList(String emailVerificationId) throws MailerSendException {
    	String endpoint = "/email-verification/".concat(emailVerificationId).concat(prepareGetListParamsUrl());

    	MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());

        SingleEmailVerificationListResponse response = api.getRequest(endpoint, SingleEmailVerificationListResponse.class);

        response.list.postDeserialize();

        // reset getList-specific filters
        detailedFilter = null;
        getListPageFilter = 1;
        getListLimitFilter = 25;

        return response.list;
    }
    
    /**
     * <p>verifyList.</p>
     *
     * @param listId a {@link java.lang.String} object.
     * @return a {@link com.mailersend.sdk.emailverification.EmailVerificationList} object.
     * @throws com.mailersend.sdk.exceptions.MailerSendException if any.
     */
    public EmailVerificationList verifyList(String listId) throws MailerSendException {
    	String endpoint = "/email-verification/".concat(listId).concat("/verify");
    	
    	MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());
        
        SingleEmailVerificationListResponse response = api.getRequest(endpoint, SingleEmailVerificationListResponse.class);
        
        response.list.postDeserialize();
        
        return response.list;
    }
    
    /**
     * <p>verificationResults.</p>
     *
     * @param listId a {@link java.lang.String} object.
     * @return a {@link com.mailersend.sdk.emailverification.ListVerificationResults} object.
     * @throws com.mailersend.sdk.exceptions.MailerSendException if any.
     */
    public ListVerificationResults verificationResults(String listId) throws MailerSendException {
    	String endpoint = "/email-verification/".concat(listId).concat("/results").concat(prepareVerificationResultsParamsUrl());

    	MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());

        ListVerificationResults response = api.getRequest(endpoint, ListVerificationResults.class);

        // reset results-specific filters
        resultsPageFilter = 1;
        resultsLimitFilter = 25;
        resultsFilter = null;

        return response;
    }
    
    /**
     * Verify a single email address synchronously.
     * POST /v1/email-verification/verify
     *
     * @param email the email address to verify.
     * @return a {@link com.mailersend.sdk.emailverification.SingleEmailVerificationResponse} object.
     * @throws com.mailersend.sdk.exceptions.MailerSendException if any.
     */
    public SingleEmailVerificationResponse verifyEmail(String email) throws MailerSendException {
        if (email == null || email.isEmpty()) {
            throw new MailerSendException("Email cannot be null or empty");
        }

        String endpoint = "/email-verification/verify";

        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());

        Gson gson = new GsonBuilder()
                .addSerializationExclusionStrategy(new JsonSerializationDeserializationStrategy(false))
                .addDeserializationExclusionStrategy(new JsonSerializationDeserializationStrategy(true))
                .create();

        String json = gson.toJson(new EmailBody(email));

        return api.postRequest(endpoint, json, SingleEmailVerificationResponse.class);
    }

    /**
     * Verify a single email address asynchronously.
     * POST /v1/email-verification/verify-async
     *
     * @param email the email address to verify.
     * @return a {@link com.mailersend.sdk.emailverification.AsyncEmailVerificationResponse} object.
     * @throws com.mailersend.sdk.exceptions.MailerSendException if any.
     */
    public AsyncEmailVerificationResponse verifyEmailAsync(String email) throws MailerSendException {
        if (email == null || email.isEmpty()) {
            throw new MailerSendException("Email cannot be null or empty");
        }

        String endpoint = "/email-verification/verify-async";

        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());

        Gson gson = new GsonBuilder()
                .addSerializationExclusionStrategy(new JsonSerializationDeserializationStrategy(false))
                .addDeserializationExclusionStrategy(new JsonSerializationDeserializationStrategy(true))
                .create();

        String json = gson.toJson(new EmailBody(email));

        return api.postRequest(endpoint, json, AsyncEmailVerificationResponse.class);
    }

    /**
     * Get the status of an async email verification.
     * GET /v1/email-verification/verify-async/{id}
     *
     * @param id the async verification id.
     * @return a {@link com.mailersend.sdk.emailverification.AsyncEmailVerificationResponse} object.
     * @throws com.mailersend.sdk.exceptions.MailerSendException if any.
     */
    public AsyncEmailVerificationResponse getVerifyEmailAsyncStatus(String id) throws MailerSendException {
        if (id == null || id.isEmpty()) {
            throw new MailerSendException("Verification ID cannot be null or empty");
        }

        String endpoint = "/email-verification/verify-async/".concat(id);

        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());

        return api.getRequest(endpoint, AsyncEmailVerificationResponse.class);
    }

    /**
     * Prepares the query part of the request url for getList
     * @return
     */
    private String prepareGetListParamsUrl() {

        ArrayList<String> params = new ArrayList<String>();

        if (detailedFilter != null) {
            params.add("detailed=".concat(detailedFilter ? "true" : "false"));
        }

        if (detailedFilter != null && detailedFilter) {
            params.add("page=".concat(String.valueOf(getListPageFilter)));
            params.add("limit=".concat(String.valueOf(getListLimitFilter)));
        }

        if (params.isEmpty()) {
            return "";
        }

        StringBuilder requestParams = new StringBuilder();
        for (int i = 0; i < params.size(); i++) {
            requestParams.append(i == 0 ? "?" : "&").append(params.get(i));
        }

        return requestParams.toString();
    }

    /**
     * Prepares the query part of the request url for verificationResults
     * @return
     */
    private String prepareVerificationResultsParamsUrl() {

        ArrayList<String> params = new ArrayList<String>();

        params.add("page=".concat(String.valueOf(resultsPageFilter)));
        params.add("limit=".concat(String.valueOf(resultsLimitFilter)));

        if (resultsFilter != null) {
            for (String result : resultsFilter) {
                params.add("results[]=".concat(result));
            }
        }

        StringBuilder requestParams = new StringBuilder();
        for (int i = 0; i < params.size(); i++) {
            requestParams.append(i == 0 ? "?" : "&").append(params.get(i));
        }

        return requestParams.toString();
    }

    /**
     * Simple inner class for the email body used by single-email verify endpoints.
     */
    private static class EmailBody {
        @com.google.gson.annotations.SerializedName("email")
        public String email;

        public EmailBody(String email) {
            this.email = email;
        }
    }

    /**
     * Prepares the query part of the request url
     * @return
     */
    private String prepareParamsUrl() {
        
        // prepare the request parameters
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
