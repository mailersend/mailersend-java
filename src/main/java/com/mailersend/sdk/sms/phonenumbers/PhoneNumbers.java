/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.sms.phonenumbers;

import java.util.ArrayList;
import java.util.stream.IntStream;

import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendApi;
import com.mailersend.sdk.MailerSendResponse;
import com.mailersend.sdk.exceptions.MailerSendException;

/**
 * <p>PhoneNumbers class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class PhoneNumbers {

    private MailerSend apiObjectReference;
    
    private int pageFilter = 1;
    private int limitFilter = 25;
    private Boolean pausedFilter;
    
    /**
     * <p>Constructor for PhoneNumbers.</p>
     *
     * @param ref a {@link com.mailersend.sdk.MailerSend} object.
     */
    public PhoneNumbers(MailerSend ref) {
    	apiObjectReference = ref;
    }
    
    /**
     * <p>page.</p>
     *
     * @param page a int.
     * @return a {@link com.mailersend.sdk.sms.phonenumbers.PhoneNumbers} object.
     */
    public PhoneNumbers page(int page) {
    	pageFilter = page;
    	return this;
    }
    
    /**
     * <p>limit.</p>
     *
     * @param limit a int.
     * @return a {@link com.mailersend.sdk.sms.phonenumbers.PhoneNumbers} object.
     */
    public PhoneNumbers limit(int limit) {
    	limitFilter = limit;
    	return this;
    }
    
    /**
     * <p>paused.</p>
     *
     * @param paused a boolean.
     * @return a {@link com.mailersend.sdk.sms.phonenumbers.PhoneNumbers} object.
     */
    public PhoneNumbers paused(boolean paused) {
    	pausedFilter = paused;
    	return this;
    }
    
    /**
     * <p>getPhoneNumbers.</p>
     *
     * @return a {@link com.mailersend.sdk.sms.phonenumbers.PhoneNumberList} object.
     * @throws com.mailersend.sdk.exceptions.MailerSendException if any.
     */
    public PhoneNumberList getPhoneNumbers() throws MailerSendException {
    	String endpoint = "/sms-numbers".concat(prepareParamsUrl());
    	
        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());
        
        PhoneNumberList response = api.getRequest(endpoint, PhoneNumberList.class);
        
        response.postDeserialize();
        
        return response;
    }
    
    /**
     * <p>getPhoneNumber.</p>
     *
     * @param phoneNumberId a {@link java.lang.String} object.
     * @return a {@link com.mailersend.sdk.sms.phonenumbers.PhoneNumber} object.
     * @throws com.mailersend.sdk.exceptions.MailerSendException if any.
     */
    public PhoneNumber getPhoneNumber(String phoneNumberId) throws MailerSendException {
    	String endpoint = "/sms-numbers/".concat(phoneNumberId);
    	
        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());
        
        SinglePhoneNumberResponse response = api.getRequest(endpoint, SinglePhoneNumberResponse.class);
        
        response.phoneNumber.postDeserialize();
        
        return response.phoneNumber;
    }
    
    
    /**
     * <p>updatePhoneNumber.</p>
     *
     * @param phoneNumberId a {@link java.lang.String} object.
     * @param paused a boolean.
     * @return a {@link com.mailersend.sdk.sms.phonenumbers.PhoneNumber} object.
     * @throws com.mailersend.sdk.exceptions.MailerSendException if any.
     */
    public PhoneNumber updatePhoneNumber(String phoneNumberId, boolean paused) throws MailerSendException {
    	String endpoint = "/sms-numbers/".concat(phoneNumberId).concat("?paused=").concat(String.valueOf(paused));
    	
        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());
        
        SinglePhoneNumberResponse response = api.putRequest(endpoint, null, SinglePhoneNumberResponse.class);
        
        response.phoneNumber.postDeserialize();
        
        return response.phoneNumber;
    }
    
    
    /**
     * <p>deletePhoneNumber.</p>
     *
     * @param phoneNumberId a {@link java.lang.String} object.
     * @return a boolean.
     * @throws com.mailersend.sdk.exceptions.MailerSendException if any.
     */
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
