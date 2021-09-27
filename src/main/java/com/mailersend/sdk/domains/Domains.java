/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.domains;

import java.util.ArrayList;
import java.util.Arrays;

import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendApi;
import com.mailersend.sdk.MailerSendResponse;
import com.mailersend.sdk.exceptions.MailerSendException;

public class Domains {

    private MailerSend apiObjectReference;
    
    private int pageFilter = 1;
    private int limitFilter = 25;
    private int verifiedOnly = -1; // we are using an int instead of a bool because we need 3 states (true - only verified, false - only unverified, ommit - all)
    
    public DomainAddBuilder addDomainBuilder;
    public DomainSettingsUpdateBuilder updateDomainSettingsBuilder;
    
    /**
     * Do not initialize directly. This should only be accessed from MailerSend.analytics
     * @param apiReference
     */
    public Domains(MailerSend ref) {
        
        apiObjectReference = ref;
        addDomainBuilder = new DomainAddBuilder(ref);
        updateDomainSettingsBuilder = new DomainSettingsUpdateBuilder(ref);
    }
    
    
    /**
     * Set the page of the request
     * @param page
     * @return
     */
    public Domains page(int page) {
        
        pageFilter = page;
        
        return this;
    }
    
    
    /**
     * Set the results limit (10 - 100)
     * @param limit
     * @return
     */
    public Domains limit(int limit) {
        
        limitFilter = limit;
        
        return this;
    }
    
    
    /**
     * Return only verified domains
     * @return
     */
    public Domains onlyVerified() {
        
        verifiedOnly = 1;
        
        return this;
    }
    
    
    /**
     * Return only unverified domains 
     * @return
     */
    public Domains onlyUnverified() {
        
        verifiedOnly = 0;
        
        return this;
    }
    
    
    /**
     * Return both unverified and verified domains
     * @return
     */
    public Domains all() {
        
        verifiedOnly = -1;
        
        return this;
    }
    
    
    /**
     * Gets a list of domains
     * @return
     * @throws MailerSendException
     */
    public DomainsList getDomains() throws MailerSendException {
        
        String endpoint = "/domains".concat(prepareParamsUrl());
        
        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());
        
        DomainsList response = api.getRequest(endpoint, DomainsList.class);
        
        return response;
    }
    
    
    /**
     * Gets a single domain
     * @param domainId
     * @return
     * @throws MailerSendException
     */
    public Domain getDomain(String domainId) throws MailerSendException {
        
        String endpoint = "/domains/".concat(domainId);
        
        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());
        
        SingleDomainResponse response = api.getRequest(endpoint, SingleDomainResponse.class);
        
        return response.domain;
    }
    
    
    /**
     * Delete a single domain
     * @param domainId
     * @return
     * @throws MailerSendException
     */
    public boolean deleteDomain(String domainId) throws MailerSendException {
        
        String endpoint = "/domains/".concat(domainId);
        
        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());
        
        MailerSendResponse response = api.deleteRequest(endpoint, MailerSendResponse.class);
        
        // return true if the response is 200, 204 or 202
        return Arrays.asList(new int[] {200, 204, 202}).contains(response.responseStatusCode);
    }
    
    
    /**
     * Gets the recipients of a domain
     * @param domainId
     * @return
     * @throws MailerSendException
     */
    public DomainRecipientsList getDomainRecipients(String domainId) throws MailerSendException {
        
        String endpoint = "/domains/".concat(domainId).concat("/recipients").concat(prepareParamsUrl());
        
        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());
        
        DomainRecipientsList response = api.getRequest(endpoint, DomainRecipientsList.class);
        
        response.postProcessing();
        
        return response;
    }
    
    
    /**
     * Gets the DNS records of a domain
     * @param domainId
     * @return
     * @throws MailerSendException
     */
    public DomainDnsRecords getDomainDnsRecords(String domainId) throws MailerSendException {
        
        String endpoint = "/domains/".concat(domainId).concat("/dns-records");
        
        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());
        
        DomainDnsRecordsResponse response = api.getRequest(endpoint, DomainDnsRecordsResponse.class);
        
        return response.records;
    }
    
    
    /**
     * Verifies the domain with the given id
     * @param domainId
     * @return
     * @throws MailerSendException
     */
    public DomainVerificationStatus verifyDomain(String domainId) throws MailerSendException {
        
        String endpoint = "/domains/".concat(domainId).concat("/verify");
        
        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());
        
        DomainVerificationStatus response = api.getRequest(endpoint, DomainVerificationStatus.class);
        
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
        
        if (verifiedOnly == 0) {
            
            params.add("verified=false");
        } else if (verifiedOnly == 1) {
            
            params.add("verified=true");
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
