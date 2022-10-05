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
 * <p>DomainAddBuilder class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class DomainAddBuilder {
  
    private MailerSend apiObjectReference;
    
    private DomainAddRequestBody domainAddBody;
    
    
    /**
     * No instantiation from outside the sdk
     *
     * @param apiObjectRef a {@link com.mailersend.sdk.MailerSend} object.
     */
    protected DomainAddBuilder(MailerSend apiObjectRef) {
        
        apiObjectReference = apiObjectRef;
        domainAddBody = new DomainAddRequestBody();
    }
    
    
    /**
     * Set the return path subdomain value
     *
     * @param returnPathSubdomain a {@link java.lang.String} object.
     * @return a {@link com.mailersend.sdk.domains.DomainAddBuilder} object.
     */
    public DomainAddBuilder returnPathSubdomain(String returnPathSubdomain) {
        
        domainAddBody.returnPathSubdomainValue = returnPathSubdomain;
        
        return this;
    }
    
    
    /**
     * Set the custom tracking domain value
     *
     * @param customTrackingSubdomain a {@link java.lang.String} object.
     * @return a {@link com.mailersend.sdk.domains.DomainAddBuilder} object.
     */
    public DomainAddBuilder customTrackingSubdomain(String customTrackingSubdomain) {
        
        domainAddBody.customTrackingSubdomainValue = customTrackingSubdomain;
        
        return this;
    }
    
    
    /**
     * Set the inbound routing subdomain value
     *
     * @param inboundRoutingSubdomain a {@link java.lang.String} object.
     * @return a {@link com.mailersend.sdk.domains.DomainAddBuilder} object.
     */
    public DomainAddBuilder inboundRoutingSubdomain(String inboundRoutingSubdomain) {
        
        domainAddBody.inboundRoutingSubdomainValue = inboundRoutingSubdomain;
        
        return this;
    }
    
    
    /**
     * Adds a domain to MailerSend
     *
     * @param name a {@link java.lang.String} object.
     * @throws com.mailersend.sdk.exceptions.MailerSendException
     * @return a {@link com.mailersend.sdk.domains.Domain} object.
     */
    public Domain addDomain(String name) throws MailerSendException {
        
        String endpoint = "/domains";
        
        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());
        
        domainAddBody.name = name;
        
        Gson gson = new GsonBuilder()
                .addSerializationExclusionStrategy(new JsonSerializationDeserializationStrategy(false))
                .addDeserializationExclusionStrategy(new JsonSerializationDeserializationStrategy(true))
                .create();
        
        String json = gson.toJson(domainAddBody);
        
        // reset the body object's values so that it can be reused
        domainAddBody.reset();
        
        SingleDomainResponse response = api.postRequest(endpoint, json, SingleDomainResponse.class);
        
        return response.domain;
    }
}
