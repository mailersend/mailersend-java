package com.mailersend.sdk.sms.phonenumbers;

import java.util.ArrayList;
import java.util.stream.IntStream;

import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendApi;
import com.mailersend.sdk.MailerSendResponse;
import com.mailersend.sdk.exceptions.MailerSendException;

public class PhoneNumbers {

    private MailerSend apiObjectReference;
    
    private int pageFilter = 1;
    private int limitFilter = 25;
    private Boolean pausedFilter;
    
    public PhoneNumbers(MailerSend ref) {
    	apiObjectReference = ref;
    }
    
    public PhoneNumbers page(int page) {
    	pageFilter = page;
    	return this;
    }
    
    public PhoneNumbers limit(int limit) {
    	limitFilter = limit;
    	return this;
    }
    
    public PhoneNumbers paused(boolean paused) {
    	pausedFilter = paused;
    	return this;
    }
    
    public PhoneNumberList getPhoneNumbers() throws MailerSendException {
    	String endpoint = "/sms-numbers".concat(prepareParamsUrl());
    	
        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());
        
        PhoneNumberList response = api.getRequest(endpoint, PhoneNumberList.class);
        
        response.postDeserialize();
        
        return response;
    }
    
    public PhoneNumber getPhoneNumber(String phoneNumberId) throws MailerSendException {
    	String endpoint = "/sms-numbers/".concat(phoneNumberId);
    	
        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());
        
        SinglePhoneNumberResponse response = api.getRequest(endpoint, SinglePhoneNumberResponse.class);
        
        response.phoneNumber.postDeserialize();
        
        return response.phoneNumber;
    }
    
    
    public PhoneNumber updatePhoneNumber(String phoneNumberId, boolean paused) throws MailerSendException {
    	String endpoint = "/sms-numbers/".concat(phoneNumberId).concat("?paused=").concat(String.valueOf(paused));
    	
        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());
        
        SinglePhoneNumberResponse response = api.putRequest(endpoint, null, SinglePhoneNumberResponse.class);
        
        response.phoneNumber.postDeserialize();
        
        return response.phoneNumber;
    }
    
    
    public boolean deletePhoneNumber(String phoneNumberId) throws MailerSendException {
    	String endpoint = "/sms-numbers/".concat(phoneNumberId);
    	
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
        
        // prepare the request parameters
        ArrayList<String> params = new ArrayList<String>();

        params.add("page=".concat(String.valueOf(pageFilter)));
        
        params.add("limit=".concat(String.valueOf(limitFilter)));
  
        if (pausedFilter != null) {
        	
        	params.add("paused=".concat(pausedFilter.toString()));
        }
        
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
