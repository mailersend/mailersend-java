/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.webhooks;

import java.util.ArrayList;
import java.util.stream.IntStream;

import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendApi;
import com.mailersend.sdk.MailerSendResponse;
import com.mailersend.sdk.exceptions.MailerSendException;

/**
 * <p>Webhooks class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class Webhooks {
    
    private MailerSend apiObjectReference;
    
    private WebhooksBuilder webhooksBuilder;
    
    private int pageFilter = 1;
    private int limitFilter = 25;
    
    
    /**
     * Do not initialize directly. This should only be accessed from MailerSend.webhooks
     *
     * @param apiObjectRef a {@link com.mailersend.sdk.MailerSend} object.
     */
    public Webhooks(MailerSend apiObjectRef) {
        
        apiObjectReference = apiObjectRef;
        webhooksBuilder = new WebhooksBuilder(apiObjectRef);
    }
    
    
    /**
     * Set the page of the request
     *
     * @param page a int.
     * @return a {@link com.mailersend.sdk.webhooks.Webhooks} object.
     */
    public Webhooks page(int page) {
        
        pageFilter = page;
        
        return this;
    }
    
    
    /**
     * Set the results limit (10 - 100)
     *
     * @param limit a int.
     * @return a {@link com.mailersend.sdk.webhooks.Webhooks} object.
     */
    public Webhooks limit(int limit) {
        
        limitFilter = limit;
        
        return this;
    }
    
    
    /**
     * Gets the builder that can be used to create or update webhooks
     *
     * @return a {@link com.mailersend.sdk.webhooks.WebhooksBuilder} object.
     */
    public WebhooksBuilder builder() {
        
        return webhooksBuilder;
    }
    
    
    /**
     * Gets a list of webhooks
     *
     * @param domainId a {@link java.lang.String} object.
     * @throws com.mailersend.sdk.exceptions.MailerSendException
     * @return a {@link com.mailersend.sdk.webhooks.WebhooksList} object.
     */
    public WebhooksList getWebhooks(String domainId) throws MailerSendException {
        
        String endpoint = "/webhooks".concat(prepareParamsUrl(domainId));
        
        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());
        
        WebhooksList response = api.getRequest(endpoint, WebhooksList.class);
        
        response.postDeserialize();
        
        return response;
    }
    
    
    /**
     * Gets a single webhook
     *
     * @param webhookId a {@link java.lang.String} object.
     * @throws com.mailersend.sdk.exceptions.MailerSendException
     * @return a {@link com.mailersend.sdk.webhooks.Webhook} object.
     */
    public Webhook getWebhook(String webhookId) throws MailerSendException {
        
        String endpoint = "/webhooks/".concat(webhookId);
        
        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());
        
        WebhookResponse response = api.getRequest(endpoint, WebhookResponse.class);
        
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
        
        String endpoint = "/webhooks/".concat(webhookId);
        
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
    private String prepareParamsUrl(String domainId) {
        
        // prepare the request parameters
        ArrayList<String> params = new ArrayList<String>();

        params.add("page=".concat(String.valueOf(pageFilter)));
        
        params.add("limit=".concat(String.valueOf(limitFilter)));
        
        params.add("domain_id=".concat(domainId));
        
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
