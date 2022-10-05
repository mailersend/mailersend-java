/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.sms.messages;

import java.util.ArrayList;

import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendApi;
import com.mailersend.sdk.exceptions.MailerSendException;

/**
 * <p>SmsMessages class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class SmsMessages {
    
	private MailerSend apiObjectReference;
    
    private int pageFilter = 1;
    private int limitFilter = 25;
    
    /**
     * <p>Constructor for SmsMessages.</p>
     *
     * @param ref a {@link com.mailersend.sdk.MailerSend} object.
     */
    public SmsMessages(MailerSend ref) {
    	apiObjectReference = ref;
    }
    
    /**
     * <p>page.</p>
     *
     * @param page a int.
     * @return a {@link com.mailersend.sdk.sms.messages.SmsMessages} object.
     */
    public SmsMessages page(int page) {
    	pageFilter = page;
    	return this;
    }
    
    /**
     * <p>limit.</p>
     *
     * @param limit a int.
     * @return a {@link com.mailersend.sdk.sms.messages.SmsMessages} object.
     */
    public SmsMessages limit(int limit) {
    	limitFilter = limit;
    	return this;
    }
    
    
    /**
     * <p>getMessages.</p>
     *
     * @return a {@link com.mailersend.sdk.sms.messages.SmsMessageList} object.
     * @throws com.mailersend.sdk.exceptions.MailerSendException if any.
     */
    public SmsMessageList getMessages() throws MailerSendException {
    	
    	String endpoint = "/sms-messages".concat(prepareParamsUrl());
    	
        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());
        
        SmsMessageList response = api.getRequest(endpoint, SmsMessageList.class);
        
        response.postDeserialize();
        
        return response;
    }
    
    /**
     * <p>getMessage.</p>
     *
     * @param messageId a {@link java.lang.String} object.
     * @return a {@link com.mailersend.sdk.sms.messages.SmsMessage} object.
     * @throws com.mailersend.sdk.exceptions.MailerSendException if any.
     */
    public SmsMessage getMessage(String messageId) throws MailerSendException {
    
    	String endpoint = "/sms-messages/".concat(messageId);
    	
        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());
        
        SingleSmsMessageResponse response = api.getRequest(endpoint, SingleSmsMessageResponse.class);
        
        response.message.postDeserialize();
        
        return response.message;
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
