/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.webhooks;

import java.util.Arrays;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendApi;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.util.JsonSerializationDeserializationStrategy;

public class WebhooksBuilder {

    private WebhooksBuilderBody builderBody = new WebhooksBuilderBody();
    
    private MailerSend apiObjectReference;
    
    
    /**
     * No instantiation from outside the sdk
     * @param apiObjectRef
     */
    protected WebhooksBuilder(MailerSend apiObjectRef) {
        
        apiObjectReference = apiObjectRef;
        
    }
    
    
    /**
     * Set the webhook's url
     * @param url
     * @return
     */
    public WebhooksBuilder url(String url) {
        
        builderBody.url = url;
        
        return this;
    }
    
    
    /**
     * Set the webhook's name
     * @param name
     * @return
     */
    public WebhooksBuilder name(String name) {
        
        builderBody.name = name;
        
        return this;
    }
    
    
    /**
     * Add an event for the webhook
     * @param event
     * @return
     */
    public WebhooksBuilder addEvent(String event) {
        
        builderBody.events.add(event);
        
        return this;
    }
    
    
    /**
     * Clears the events of the webhook request
     * @return
     */
    public WebhooksBuilder clearEvents() {
        
        builderBody.events.clear();
        
        return this;
    }
    
    
    /**
     * Creates a webhook
     * @param domainId
     * @return
     * @throws MailerSendException
     */
    public Webhook createWebhook(String domainId) throws MailerSendException {
        
        builderBody.domainId = domainId;
        
        if (builderBody.name == null || builderBody.name.isBlank()) {
            
            throw new MailerSendException("Webhook name cannot be empty");
        }
        
        if (builderBody.url == null || builderBody.url.isBlank()) {
            
            throw new MailerSendException("Webhook URL cannot be empty");
        }
        
        if (domainId == null || domainId.isBlank()) {
            
            throw new MailerSendException("Domain ID cannot be empty");
        }
        
        
        for (String event : builderBody.events) {
            if (!Arrays.asList(WebhookEvents.events).contains(event)) {
                
                throw new MailerSendException("Webhook event is not valid");
            }
        }
        
        String endpoint = "/webhooks";
        
        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());
        
        Gson gson = new GsonBuilder()
                .addSerializationExclusionStrategy(new JsonSerializationDeserializationStrategy(false))
                .addDeserializationExclusionStrategy(new JsonSerializationDeserializationStrategy(true))
                .create();
        
        String json = gson.toJson(builderBody);
        
        // reset the body object's values so that it can be reused
        builderBody.reset();
        
        WebhookResponse response = api.postRequest(endpoint, json, WebhookResponse.class);
        
        return response.webhook;
    }
    
    
    /**
     * Updates the webhook with the given id
     * @param domainId
     * @return
     * @throws MailerSendException
     */
    public Webhook updateWebhook(String webhookId) throws MailerSendException {
        
        if (webhookId == null || webhookId.isBlank()) {
            
            throw new MailerSendException("Domain ID cannot be empty");
        }
        
        String endpoint = "/webhooks/".concat(webhookId);
        
        if (builderBody.events.size() == 0) {
            
            builderBody.events = null;
        } else {
            
            for (String event : builderBody.events) {
                if (!Arrays.asList(WebhookEvents.events).contains(event)) {
                    
                    throw new MailerSendException("Webhook event is not valid");
                }
            }
        }
        
        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());
        
        Gson gson = new GsonBuilder()
                .addSerializationExclusionStrategy(new JsonSerializationDeserializationStrategy(false))
                .addDeserializationExclusionStrategy(new JsonSerializationDeserializationStrategy(true))
                .create();
        
        String json = gson.toJson(builderBody);
        
        // reset the body object's values so that it can be reused
        builderBody.reset();
        
        WebhookResponse response = api.putRequest(endpoint, json, WebhookResponse.class);
        
        return response.webhook;
    }
    
}
