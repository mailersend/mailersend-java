package com.mailsend.sdk.emailverification;

import java.util.ArrayList;

import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendApi;
import com.mailersend.sdk.exceptions.MailerSendException;

public class EmailVerification {
    private MailerSend apiObjectReference;
    
    private int pageFilter = 1;
    private int limitFilter = 25;
    
    private EmailVerificationBuilder builder;
    
    public EmailVerification(MailerSend ref) {
    	apiObjectReference = ref;
    	builder = new EmailVerificationBuilder(ref);
    }
    
    public EmailVerification page(int page) {
    	pageFilter = page;
    	return this;
    }
    
    public EmailVerification limit(int limit) {
    	limitFilter = limit;
    	return this;
    }
    
    public EmailVerificationBuilder builder() {
    	return builder;
    }
    
    public EmailVerificationBuilder newBuilder() {
    	return new EmailVerificationBuilder(apiObjectReference);
    }
    
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
    
    public EmailVerificationList getList(String emailVerificationId) throws MailerSendException {
    	String endpoint = "/email-verification/".concat(emailVerificationId);
    	
    	MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());
        
        SingleEmailVerificationListResponse response = api.getRequest(endpoint, SingleEmailVerificationListResponse.class);
        
        response.list.postDeserialize();
        
        return response.list;
    }
    
    public EmailVerificationList verifyList(String listId) throws MailerSendException {
    	String endpoint = "/email-verification/".concat(listId).concat("/verify");
    	
    	MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());
        
        SingleEmailVerificationListResponse response = api.getRequest(endpoint, SingleEmailVerificationListResponse.class);
        
        response.list.postDeserialize();
        
        return response.list;
    }
    
    public ListVerificationResults verificationResults(String listId) throws MailerSendException {
    	String endpoint = "/email-verification/".concat(listId).concat("/results");
    	
    	MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());
        
        ListVerificationResults response = api.getRequest(endpoint, ListVerificationResults.class);
        
        return response;
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
