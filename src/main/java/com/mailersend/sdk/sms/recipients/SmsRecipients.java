/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.sms.recipients;

import java.util.ArrayList;

import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendApi;
import com.mailersend.sdk.exceptions.MailerSendException;

/**
 * <p>SmsRecipients class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class SmsRecipients {

	private MailerSend apiObjectReference;
	
    private int pageFilter = 1;
    private int limitFilter = 25;
    private String statusFilter;
    private String numberIdFilter;
    
    /**
     * <p>Constructor for SmsRecipients.</p>
     *
     * @param ref a {@link com.mailersend.sdk.MailerSend} object.
     */
    public SmsRecipients(MailerSend ref) {
    	apiObjectReference = ref;
    }
    
    /**
     * <p>page.</p>
     *
     * @param page a int.
     * @return a {@link com.mailersend.sdk.sms.recipients.SmsRecipients} object.
     */
    public SmsRecipients page(int page) {
    	pageFilter = page;
    	return this;
    }
    
    
    /**
     * <p>limit.</p>
     *
     * @param limit a int.
     * @return a {@link com.mailersend.sdk.sms.recipients.SmsRecipients} object.
     */
    public SmsRecipients limit(int limit) {
    	limitFilter = limit;
    	return this;
    }
    
    /**
     * <p>status.</p>
     *
     * @param status a {@link java.lang.String} object.
     * @return a {@link com.mailersend.sdk.sms.recipients.SmsRecipients} object.
     */
    public SmsRecipients status(String status) {
    	statusFilter = status;
    	return this;
    }
    
    /**
     * <p>numberId.</p>
     *
     * @param numberId a {@link java.lang.String} object.
     * @return a {@link com.mailersend.sdk.sms.recipients.SmsRecipients} object.
     */
    public SmsRecipients numberId(String numberId) {
    	numberIdFilter = numberId;
    	return this;
    }
    
    /**
     * <p>getRecipients.</p>
     *
     * @return a {@link com.mailersend.sdk.sms.recipients.SmsRecipientList} object.
     * @throws com.mailersend.sdk.exceptions.MailerSendException if any.
     */
    public SmsRecipientList getRecipients() throws MailerSendException {
    	String endpoint = "/sms-recipients".concat(prepareParamsUrl());
    	
        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());
        
        SmsRecipientList response = api.getRequest(endpoint, SmsRecipientList.class);
        response.postDeserialize();
        
        return response;
    }
    
    /**
     * <p>getRecipient.</p>
     *
     * @param recipientId a {@link java.lang.String} object.
     * @return a {@link com.mailersend.sdk.sms.recipients.SmsRecipient} object.
     * @throws com.mailersend.sdk.exceptions.MailerSendException if any.
     */
    public SmsRecipient getRecipient(String recipientId) throws MailerSendException {
    	String endpoint = "/sms-recipients/".concat(recipientId);
    	
        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());
        
        SingleSmsRecipientResponse response = api.getRequest(endpoint, SingleSmsRecipientResponse.class);
        
        response.recipient.postDeserialize();
        
        return response.recipient;
    }
    
    /**
     * <p>updateRecipient.</p>
     *
     * @param recipientId a {@link java.lang.String} object.
     * @param status a {@link java.lang.String} object.
     * @return a {@link com.mailersend.sdk.sms.recipients.SmsRecipient} object.
     * @throws com.mailersend.sdk.exceptions.MailerSendException if any.
     */
    public SmsRecipient updateRecipient(String recipientId, String status) throws MailerSendException {
    	String endpoint = "/sms-recipients/".concat(recipientId).concat("?status=").concat(status);
    	
        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());
        
        SingleSmsRecipientResponse response = api.putRequest(endpoint, null, SingleSmsRecipientResponse.class);
        
        response.recipient.postDeserialize();
        
        return response.recipient;
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
        
        if (statusFilter != null) {
        	params.add("status=".concat(statusFilter));
        }
        
        if (numberIdFilter != null) {
        	params.add("sms_number_id=".concat(numberIdFilter));
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
