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

/**
 * <p>Recipients class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class Recipients {

    
    private MailerSend apiObjectReference;
    
    private int pageFilter = 1;
    private int limitFilter = 25;
    private String domainIdFilter = null;
    
    private Suppressions suppressions;
    
    
    /**
     * Do not initialize directly. This should only be accessed from MailerSend.analytics
     *
     * @param ref a {@link com.mailersend.sdk.MailerSend} object.
     */
    public Recipients(MailerSend ref) {
        
        apiObjectReference = ref;
        suppressions = new Suppressions(ref);
    }
    
    
    /**
     * Get the Suppressions object instance to query the suppressions
     *
     * @return a {@link com.mailersend.sdk.recipients.Suppressions} object.
     */
    public Suppressions suppressions() {
        
        return suppressions;
    }

    
    /**
     * Set the page of the request
     *
     * @param page a int.
     * @return a {@link com.mailersend.sdk.recipients.Recipients} object.
     */
    public Recipients page(int page) {
        
        pageFilter = page;
        
        return this;
    }
    
    
    /**
     * Set the results limit (10 - 100)
     *
     * @param limit a int.
     * @return a {@link com.mailersend.sdk.recipients.Recipients} object.
     */
    public Recipients limit(int limit) {
        
        limitFilter = limit;
        
        return this;
    }
    
    
    /**
     * Set the domain ID
     *
     * @param domainId a {@link java.lang.String} object.
     * @return a {@link com.mailersend.sdk.recipients.Recipients} object.
     */
    public Recipients domainId(String domainId) {
        
        domainIdFilter = domainId;
        
        return this;
    }
    
    
    /**
     * Get recipients
     *
     * @throws com.mailersend.sdk.exceptions.MailerSendException
     * @return a {@link com.mailersend.sdk.util.ApiRecipientsList} object.
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
     *
     * @param recipientId a {@link java.lang.String} object.
     * @throws com.mailersend.sdk.exceptions.MailerSendException
     * @return a {@link com.mailersend.sdk.recipients.Recipient} object.
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
     *
     * @param recipientId a {@link java.lang.String} object.
     * @throws com.mailersend.sdk.exceptions.MailerSendException
     * @return a {@link com.mailersend.sdk.MailerSendResponse} object.
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
