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

/**
 * <p>DomainSettingsUpdateBuilder class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class DomainSettingsUpdateBuilder {

    private MailerSend apiObjectReference;
    
    private DomainSettings settings;
    
    
    /**
     * Protected constructor, should only be instantiated by the sdk
     *
     * @param apiObjectRef a {@link com.mailersend.sdk.MailerSend} object.
     */
    protected DomainSettingsUpdateBuilder(MailerSend apiObjectRef) {
        
        apiObjectReference = apiObjectRef;
        settings = new DomainSettings();
    }
    
    
    /**
     * Set sendings pause on/off
     *
     * @param sendPaused a boolean.
     * @return a {@link com.mailersend.sdk.domains.DomainSettingsUpdateBuilder} object.
     */
    public DomainSettingsUpdateBuilder sendPaused(boolean sendPaused) {
        
        settings.sendPaused = sendPaused;
        
        return this;
    }
    
    
    /**
     * Set click tracking on/off
     *
     * @param trackClicks a boolean.
     * @return a {@link com.mailersend.sdk.domains.DomainSettingsUpdateBuilder} object.
     */
    public DomainSettingsUpdateBuilder trackClicks(boolean trackClicks) {
        
        settings.trackClicks = trackClicks;
        
        return this;
    }
    
    
    /**
     * Set opens tracking on/off
     *
     * @param trackOpens a boolean.
     * @return a {@link com.mailersend.sdk.domains.DomainSettingsUpdateBuilder} object.
     */
    public DomainSettingsUpdateBuilder trackOpens(boolean trackOpens) {
        
        settings.trackOpens = trackOpens;
        
        return this;
    }
    
    
    /**
     * Set unsubscribe tracking on/off
     *
     * @param trackUnsubscribe a boolean.
     * @return a {@link com.mailersend.sdk.domains.DomainSettingsUpdateBuilder} object.
     */
    public DomainSettingsUpdateBuilder trackUnsubscribe(boolean trackUnsubscribe) {
        
        settings.trackUnsubscribe = trackUnsubscribe;
        
        return this;
    }
    
    
    /**
     * Set the unsubscribe html content
     *
     * @param html a {@link java.lang.String} object.
     * @return a {@link com.mailersend.sdk.domains.DomainSettingsUpdateBuilder} object.
     */
    public DomainSettingsUpdateBuilder trackUnsubscribeHtml(String html) {
        
        settings.trackUnsubscribeHtml = html;
        
        return this;
    }
    
    
    /**
     * Set the unsubscribe plain content
     *
     * @param plain a {@link java.lang.String} object.
     * @return a {@link com.mailersend.sdk.domains.DomainSettingsUpdateBuilder} object.
     */
    public DomainSettingsUpdateBuilder trackUnsubscribePlain(String plain) {
        
        settings.trackUnsubscribePlain = plain;
        
        return this;
    }
    
    
    /**
     * Set content tracking on/off
     *
     * @param trackContent a boolean.
     * @return a {@link com.mailersend.sdk.domains.DomainSettingsUpdateBuilder} object.
     */
    public DomainSettingsUpdateBuilder trackContent(boolean trackContent) {
        
        settings.trackContent = trackContent;
        
        return this;
    }
    
    /**
     * <p>customnTrackingEnabled.</p>
     *
     * @param customTrackingEnabled a boolean.
     * @return a {@link com.mailersend.sdk.domains.DomainSettingsUpdateBuilder} object.
     */
    public DomainSettingsUpdateBuilder customnTrackingEnabled(boolean customTrackingEnabled) {
        
        settings.customTrackingEnabled = customTrackingEnabled;
        
        return this;
    }
    
    
    /**
     * Set custom subdomain tracking on/off
     *
     * @param customTrackingSubdomain a {@link java.lang.String} object.
     * @return a {@link com.mailersend.sdk.domains.DomainSettingsUpdateBuilder} object.
     */
    public DomainSettingsUpdateBuilder customTrackingSubdomain(String customTrackingSubdomain) {
        
        settings.customTrackingSubdomain = customTrackingSubdomain;
        
        return this;
    }

    /**
     * Set precedence bulk on/off
     *
     * @param precedenceBulk a boolean.
     * @return a {@link com.mailersend.sdk.domains.DomainSettingsUpdateBuilder} object.
     */
    public DomainSettingsUpdateBuilder precedenceBulk(boolean precedenceBulk) {
        
        settings.precedenceBulk = precedenceBulk;
        
        return this;
    }


    /**
     * Set ignore duplicated recipients on/off
     *
     * @param ignoreDuplicatedRecipients a boolean.
     * @return a {@link com.mailersend.sdk.domains.DomainSettingsUpdateBuilder} object.
     */
    public DomainSettingsUpdateBuilder ignoreDuplicatedRecipients(boolean ignoreDuplicatedRecipients) {
        
        settings.ignoreDuplicatedRecipients = ignoreDuplicatedRecipients;
        
        return this;
    }


    /**
     * Updates a domain's settings
     *
     * @param domainId a {@link java.lang.String} object.
     * @throws com.mailersend.sdk.exceptions.MailerSendException
     * @return a {@link com.mailersend.sdk.domains.Domain} object.
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
