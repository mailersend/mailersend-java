/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.templates;

import java.util.ArrayList;

import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendApi;
import com.mailersend.sdk.MailerSendResponse;
import com.mailersend.sdk.exceptions.MailerSendException;

public class Templates {
    
    private MailerSend apiObjectReference;
    
    private int pageFilter = 1;
    private int limitFilter = 25;
    private String domainIdFilter = null;
    
    
    /**
     * Do not initialize directly. This should only be accessed from MailerSend.analytics
     * @param apiReference
     */
    public Templates(MailerSend ref) {
        
        apiObjectReference = ref;
    }
    
    
    /**
     * Set the page of the request
     * @param page
     * @return
     */
    public Templates page(int page) {
        
        pageFilter = page;
        
        return this;
    }
    
    
    /**
     * Set the results limit (10 - 100)
     * @param limit
     * @return
     */
    public Templates limit(int limit) {
        
        limitFilter = limit;
        
        return this;
    }
    
    
    /**
     * Set the domain ID
     * @param domainId
     * @return
     */
    public Templates domainId(String domainId) {
        
        domainIdFilter = domainId;
        
        return this;
    }
    
    
    /**
     * Get templates
     * @return
     * @throws MailerSendException
     */
    public TemplatesList getTemplates() throws MailerSendException {
        
        String endpoint = "/templates".concat(prepareParamsUrl());
        
        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());
        
        TemplatesList response = api.getRequest(endpoint, TemplatesList.class);
        
        response.postDeserialize();
        
        return response;
    }
    
    
    /**
     * Retrieve the template with the given id
     * @param templateId
     * @return
     * @throws MailerSendException
     */
    public Template getTemplate(String templateId) throws MailerSendException {
        
        String endpoint = "/templates/".concat(templateId);
        
        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());
        
        TemplateResponse response = api.getRequest(endpoint, TemplateResponse.class);
        
        if (response.template != null) {
            
            response.template.postDeserialize();
        }
        
        return response.template;
    }
    
    
    /**
     * Delete the template with the given id
     * @param templateId
     * @return
     * @throws MailerSendException
     */
    public MailerSendResponse deleteTemplate(String templateId) throws MailerSendException {
        
        String endpoint = "/templates/".concat(templateId);
        
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
