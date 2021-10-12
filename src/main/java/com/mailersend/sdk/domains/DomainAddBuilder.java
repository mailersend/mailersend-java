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

public class DomainAddBuilder {
  
    private MailerSend apiObjectReference;
    
    private DomainAddRequestBody domainAddBody;
    
    
    /**
     * No instantiation from outside the sdk
     * @param apiObjectRef
     */
    protected DomainAddBuilder(MailerSend apiObjectRef) {
        
        apiObjectReference = apiObjectRef;
        domainAddBody = new DomainAddRequestBody();
    }
    
    
    /**
     * Set the return path subdomain value
     * @param returnPathSubdomain
     * @return
     */
    public DomainAddBuilder returnPathSubdomain(String returnPathSubdomain) {
        
        domainAddBody.returnPathSubdomainValue = returnPathSubdomain;
        
        return this;
    }
    
    
    /**
     * Set the custom tracking domain value
     * @param customTrackingSubdomain
     * @return
     */
    public DomainAddBuilder customTrackingSubdomain(String customTrackingSubdomain) {
        
        domainAddBody.customTrackingSubdomainValue = customTrackingSubdomain;
        
        return this;
    }
    
    
    /**
     * Set the inbound routing subdomain value
     * @param inboundRoutingSubdomain
     * @return
     */
    public DomainAddBuilder inboundRoutingSubdomain(String inboundRoutingSubdomain) {
        
        domainAddBody.inboundRoutingSubdomainValue = inboundRoutingSubdomain;
        
        return this;
    }
    
    
    /**
     * Adds a domain to MailerSend
     * @param name
     * @return
     * @throws MailerSendException
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
