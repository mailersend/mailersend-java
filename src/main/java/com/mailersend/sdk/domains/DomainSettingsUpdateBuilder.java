/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.domains;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendApi;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.util.JsonSerializationDeserializationStrategy;

public class DomainSettingsUpdateBuilder {

    private MailerSend apiObjectReference;
    
    private DomainSettings settings;
    
    
    /**
     * Protected constructor, should only be instantiated by the sdk
     * @param apiObjectRef
     */
    protected DomainSettingsUpdateBuilder(MailerSend apiObjectRef) {
        
        apiObjectReference = apiObjectRef;
        settings = new DomainSettings();
    }
    
    
    /**
     * Set sendings pause on/off
     * @param sendPaused
     * @return
     */
    public DomainSettingsUpdateBuilder sendPaused(boolean sendPaused) {
        
        settings.sendPaused = sendPaused;
        
        return this;
    }
    
    
    /**
     * Set click tracking on/off
     * @param trackClicks
     * @return
     */
    public DomainSettingsUpdateBuilder trackClicks(boolean trackClicks) {
        
        settings.trackClicks = trackClicks;
        
        return this;
    }
    
    
    /**
     * Set opens tracking on/off
     * @param trackOpens
     * @return
     */
    public DomainSettingsUpdateBuilder trackOpens(boolean trackOpens) {
        
        settings.trackOpens = trackOpens;
        
        return this;
    }
    
    
    /**
     * Set unsubscribe tracking on/off
     * @param trackUnsubscribe
     * @return
     */
    public DomainSettingsUpdateBuilder trackUnsubscribe(boolean trackUnsubscribe) {
        
        settings.trackUnsubscribe = trackUnsubscribe;
        
        return this;
    }
    
    
    /**
     * Set the unsubscribe html content
     * @param html
     * @return
     */
    public DomainSettingsUpdateBuilder trackUnsubscribeHtml(String html) {
        
        settings.trackUnsubscribeHtml = html;
        
        return this;
    }
    
    
    /**
     * Set the unsubscribe plain content
     * @param plain
     * @return
     */
    public DomainSettingsUpdateBuilder trackUnsubscribePlain(String plain) {
        
        settings.trackUnsubscribePlain = plain;
        
        return this;
    }
    
    
    /**
     * Set content tracking on/off
     * @param trackContent
     * @return
     */
    public DomainSettingsUpdateBuilder trackContent(boolean trackContent) {
        
        settings.trackContent = trackContent;
        
        return this;
    }
    
    public DomainSettingsUpdateBuilder customnTrackingEnabled(boolean customTrackingEnabled) {
        
        settings.customTrackingEnabled = customTrackingEnabled;
        
        return this;
    }
    
    
    /**
     * Set custom subdomain tracking on/off
     * @param customTrackingSubdomain
     * @return
     */
    public DomainSettingsUpdateBuilder customTrackingSubdomain(String customTrackingSubdomain) {
        
        settings.customTrackingSubdomain = customTrackingSubdomain;
        
        return this;
    }
    
    
    /**
     * Updates a domain's settings
     * @param domainId
     * @return
     * @throws MailerSendException
     */
    public Domain updateDomain(String domainId) throws MailerSendException {
        
        String endpoint = "/domains/".concat(domainId).concat("/settings");
        
        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());
        
        Gson gson = new GsonBuilder()
                .addSerializationExclusionStrategy(new JsonSerializationDeserializationStrategy(false))
                .addDeserializationExclusionStrategy(new JsonSerializationDeserializationStrategy(true))
                .create();
        
        String json = gson.toJson(settings);
        
        // reset the body object's values so that it can be reused
        settings.reset();
        
        SingleDomainResponse response = api.putRequest(endpoint, json, SingleDomainResponse.class);
        
        return response.domain;
    }
    
}
