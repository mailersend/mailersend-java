/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.tokens;

import java.util.Arrays;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendApi;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.util.JsonSerializationDeserializationStrategy;


public class TokenAddBuilder {

    private MailerSend apiObjectReference;

    private TokenAddRequestBody tokenAddBody;
    
    private final String[] scopes = {
         "email_full",
         "domains_read",
         "domains_full",
         "activity_read",
         "activity_full",
         "analytics_read",
         "analytics_full",
         "tokens_full",
         "webhooks_full",
         "templates_full"
    };
    
    /**
     * No instantiation from outside the sdk
     * @param apiObjectRef
     */
    protected TokenAddBuilder(MailerSend apiObjectRef) {
        
        apiObjectReference = apiObjectRef;
        tokenAddBody = new TokenAddRequestBody();
    }
    
    
    /**
     * Set the name parameter
     * @param name
     */
    public TokenAddBuilder name(String name) {
        
        tokenAddBody.name = name;
        
        return this;
    }
    
    
    /**
     * Set the domain id
     * @param domainId
     */
    public TokenAddBuilder domainId(String domainId) {
        
        tokenAddBody.domainId = domainId;
        
        return this;
    }
    
    
    /**
     * Add a scope
     * @param scope
     * @throws MailerSendException 
     */
    public TokenAddBuilder addScope(String scope) throws MailerSendException {
       
        if (!Arrays.asList(scopes).contains(scope)) {
        
            throw new MailerSendException("Scope is not valid");
        }
        
        tokenAddBody.scopes.add(scope);
        
        return this;
    }
    
    
    /**
     * Creates an API token
     * @return
     * @throws MailerSendException
     */
    public TokenAdd addToken() throws MailerSendException {
       
        if (tokenAddBody.name == null || tokenAddBody.name.isBlank()) {
            
            throw new MailerSendException("Token name cannot be null or empty");
        }
        
        if (tokenAddBody.domainId == null || tokenAddBody.domainId.isBlank()) {
            
            throw new MailerSendException("Domain ID cannot be null or empty");
        }
        
        if (tokenAddBody.scopes.size() == 0) {
            
            throw new MailerSendException("At least one scope is required");
        }
        
        String endpoint = "/token";
        
        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());
        
        Gson gson = new GsonBuilder()
                .addSerializationExclusionStrategy(new JsonSerializationDeserializationStrategy(false))
                .addDeserializationExclusionStrategy(new JsonSerializationDeserializationStrategy(true))
                .create();
        
        String json = gson.toJson(tokenAddBody);
        
        tokenAddBody.reset();
        
        TokenAddResponse response = api.postRequest(endpoint, json, TokenAddResponse.class);
        
        response.tokenAdd.postDeserialize();
        
        return response.tokenAdd;
    }
    
}
