/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.recipients;

import java.util.ArrayList;

import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendApi;
import com.mailersend.sdk.MailerSendResponse;
import com.mailersend.sdk.domains.DomainsList;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.util.ApiRecipientsList;

public class Recipients {

    
    private MailerSend apiObjectReference;
    
    private int pageFilter = 1;
    private int limitFilter = 25;
    private String domainIdFilter = null;
    
    private Suppressions suppressions;
    
    
    /**
     * Do not initialize directly. This should only be accessed from MailerSend.analytics
     * @param ref
     */
    public Recipients(MailerSend ref) {
        
        apiObjectReference = ref;
        suppressions = new Suppressions(ref);
    }
    
    
    /**
     * Get the Suppressions object instance to query the suppressions
     * @return
     */
    public Suppressions suppressions() {
        
        return suppressions;
    }

    
    /**
     * Set the page of the request
     * @param page
     * @return
     */
    public Recipients page(int page) {
        
        pageFilter = page;
        
        return this;
    }
    
    
    /**
     * Set the results limit (10 - 100)
     * @param limit
     * @return
     */
    public Recipients limit(int limit) {
        
        limitFilter = limit;
        
        return this;
    }
    
    
    /**
     * Set the domain ID
     * @param domainId
     * @return
     */
    public Recipients domainId(String domainId) {
        
        domainIdFilter = domainId;
        
        return this;
    }
    
    
    /**
     * Get recipients
     * @return
     * @throws MailerSendException
     */
    public ApiRecipientsList getRecipients() throws MailerSendException {
        
        String endpoint = "/recipients".concat(prepareParamsUrl());
        
        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());
        
        ApiRecipientsList response = api.getRequest(endpoint, ApiRecipientsList.class);
        
        return response;
    }
    
    
    /**
     * Get a single recipient
     * @param recipientId
     * @return
     * @throws MailerSendException
     */
    public Recipient getRecipient(String recipientId) throws MailerSendException {
        
        String endpoint = "/recipients/".concat(recipientId);
        
        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());
        
        RecipientResponse response = api.getRequest(endpoint, RecipientResponse.class);
        
        return response.recipient;
    }
    
    
    /**
     * Deletes the recipient with the given id
     * @param recipientId
     * @return
     * @throws MailerSendException
     */
    public MailerSendResponse deleteRecipient(String recipientId) throws MailerSendException {
        
        String endpoint = "/recipients/".concat(recipientId);
        
        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());
        
        MailerSendResponse response = api.deleteRequest(endpoint, MailerSendResponse.class);
        
        return response;
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
        
        if (domainIdFilter != null) {
            
            params.add("domain_id=".concat(domainIdFilter));
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
