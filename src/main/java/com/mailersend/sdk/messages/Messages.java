/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.messages;

import java.util.ArrayList;

import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendApi;
import com.mailersend.sdk.exceptions.MailerSendException;

/**
 * <p>Messages class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class Messages {

    private MailerSend apiObjectReference;
    
    private int pageFilter = 1;
    private int limitFilter = 25;
    
    /**
     * <p>Constructor for Messages.</p>
     *
     * @param ref a {@link com.mailersend.sdk.MailerSend} object.
     */
    public Messages(MailerSend ref) {
        
        apiObjectReference = ref;
    }
    
    /**
     * Set the page of the request
     *
     * @param page a int.
     * @return a {@link com.mailersend.sdk.messages.Messages} object.
     */
    public Messages page(int page) {
        
        pageFilter = page;
        
        return this;
    }
    
    
    /**
     * Set the results limit (10 - 100)
     *
     * @param limit a int.
     * @return a {@link com.mailersend.sdk.messages.Messages} object.
     */
    public Messages limit(int limit) {
        
        limitFilter = limit;
        
        return this;
    }
    
    
    /**
     * Gets a list of messages
     *
     * @throws com.mailersend.sdk.exceptions.MailerSendException
     * @return a {@link com.mailersend.sdk.messages.MessagesList} object.
     */
    public MessagesList getMessages() throws MailerSendException {
        
        String endpoint = "/messages".concat(prepareParamsUrl());
        
        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());
        
        MessagesList response = api.getRequest(endpoint, MessagesList.class);
        
        response.postProcessing();
        
        return response;
    }
    
    
    /**
     * Gets a single message
     *
     * @param messageId a {@link java.lang.String} object.
     * @throws com.mailersend.sdk.exceptions.MailerSendException
     * @return a {@link com.mailersend.sdk.messages.Message} object.
     */
    public Message getMessage(String messageId) throws MailerSendException {
        
        String endpoint = "/messages/".concat(messageId);
        
        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());
        
        SingleMessageResponse response = api.getRequest(endpoint, SingleMessageResponse.class);
        
        if (response.message != null) {
            
            response.message.parseDates();
        }
        
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
