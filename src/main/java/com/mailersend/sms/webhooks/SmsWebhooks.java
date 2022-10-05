/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sms.webhooks;

import java.util.ArrayList;
import java.util.stream.IntStream;

import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendApi;
import com.mailersend.sdk.MailerSendResponse;
import com.mailersend.sdk.exceptions.MailerSendException;

/**
 * <p>SmsWebhooks class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class SmsWebhooks {

    private MailerSend apiObjectReference;
    
    private SmsWebhooksBuilder webhooksBuilder;
    
    private int pageFilter = 1;
    private int limitFilter = 25;
    
    
    /**
     * Do not initialize directly. This should only be accessed from MailerSend.webhooks
     *
     * @param apiObjectRef a {@link com.mailersend.sdk.MailerSend} object.
     */
    public SmsWebhooks(MailerSend apiObjectRef) {
        
        apiObjectReference = apiObjectRef;
        webhooksBuilder = new SmsWebhooksBuilder(apiObjectRef);
    }
    
    
    /**
     * Set the page of the request
     *
     * @param page a int.
     * @return a {@link com.mailersend.sms.webhooks.SmsWebhooks} object.
     */
    public SmsWebhooks page(int page) {
        
        pageFilter = page;
        
        return this;
    }
    
    
    /**
     * Set the results limit (10 - 100)
     *
     * @param limit a int.
     * @return a {@link com.mailersend.sms.webhooks.SmsWebhooks} object.
     */
    public SmsWebhooks limit(int limit) {
        
        limitFilter = limit;
        
        return this;
    }
    
    
    /**
     * Gets a list of webhooks
     *
     * @throws com.mailersend.sdk.exceptions.MailerSendException
     * @param smsNumberId a {@link java.lang.String} object.
     * @return a {@link com.mailersend.sms.webhooks.SmsWebhookList} object.
     */
    public SmsWebhookList getWebhooks(String smsNumberId) throws MailerSendException {
        
        String endpoint = "/sms-webhooks".concat(prepareParamsUrl(smsNumberId));
        
        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());
        
        SmsWebhookList response = api.getRequest(endpoint, SmsWebhookList.class);
        
        response.postDeserialize();
        
        return response;
    }
    
    
    /**
     * Gets a single webhook
     *
     * @param webhookId a {@link java.lang.String} object.
     * @throws com.mailersend.sdk.exceptions.MailerSendException
     * @return a {@link com.mailersend.sms.webhooks.SmsWebhook} object.
     */
    public SmsWebhook getWebhook(String webhookId) throws MailerSendException {
        
        String endpoint = "/sms-webhooks/".concat(webhookId);
        
        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());
        
        SingleSmsWebhookResponse response = api.getRequest(endpoint, SingleSmsWebhookResponse.class);
        
        response.webhook.postDeserialize();
        
        return response.webhook;
    }
    
    
    /**
     * Deletes a webhook
     *
     * @param webhookId a {@link java.lang.String} object.
     * @throws com.mailersend.sdk.exceptions.MailerSendException
     * @return a boolean.
     */
    public boolean deleteWebhook(String webhookId) throws MailerSendException {
        
        String endpoint = "/sms-webhooks/".concat(webhookId);
        
        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());
        
        MailerSendResponse response = api.deleteRequest(endpoint, MailerSendResponse.class);
        
        // return true if the response is 200, 204 or 202
        return IntStream.of(new int[] {200, 204, 202}).anyMatch(x -> x == response.responseStatusCode);
    }
    
    /**
     * Gets the builder that can be used to create or update webhooks
     *
     * @return a {@link com.mailersend.sms.webhooks.SmsWebhooksBuilder} object.
     */
    public SmsWebhooksBuilder builder() {
        
        return webhooksBuilder;
    }
    
    
    /**
     * <p>newBuilder.</p>
     *
     * @return a {@link com.mailersend.sms.webhooks.SmsWebhooksBuilder} object.
     */
    public SmsWebhooksBuilder newBuilder() {
    	
    	return new SmsWebhooksBuilder(apiObjectReference);
    }
    
    /**
     * Prepares the query part of the request url
     * @return
     */
    private String prepareParamsUrl(String smsNumberId) {
        
        // prepare the request parameters
        ArrayList<String> params = new ArrayList<String>();

        params.add("page=".concat(String.valueOf(pageFilter)));
        
        params.add("limit=".concat(String.valueOf(limitFilter)));
        
        params.add("sms_number_id=".concat(smsNumberId));
        
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
