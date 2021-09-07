/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import com.mailersend.sdk.exceptions.MailerSendException;

/**
 * Main SDK Class
 */
public class MailerSend {

    private String token;
    private Recipient defaultFrom = null;
      
    
    /**
     * Sets the MailerSend token
     * @param token
     */
    public void setToken(String token) {
        
        this.token = token;
    }
    
    
    /**
     * Sets the default from
     * @param from
     */
    public void setDefaultFrom(Recipient from) {
        
        this.defaultFrom = from;
    }
    
    
    /**
     * Creates a new email
     * @return
     */
    public Email createEmail() {
        
        Email newEmail = new Email();
        newEmail.from = this.defaultFrom;
        
        return newEmail;
    }
    
    
    /**
     * Sends the given email
     * @param email
     * @throws MailerSendResponseError 
     */
    public MailerSendResponse send(Email email) throws MailerSendException {
        
        String json = email.serializeForSending();
        
        MailerSendApi api = new MailerSendApi();
        api.setToken(this.token);
       
        api.postRequest("/email", json, null);
        
        MailerSendResponse response = api.getLastRequestStatus();
        
        return response;
    }
    
    
    /**
     * Gets the activities for the given domain id
     * @param domainId
     * @return Activity[]
     * @throws MailerSendException
     */
    public Activity[] getActivities(String domainId) throws MailerSendException {
        
        return this.getActivities(domainId, 1, 25, null, null, null);
    }
    
    
    /**
     * Gets the activities for the given domain id. Allows for pagination and filtering
     * @param domainId The id of the domain to get the activities for
     * @param page The results page
     * @param limit How many results to return per page (default 25)
     * @param dateFrom The from date to filter the results
     * @param dateTo The to date to filter the results
     * @param events A list of events to filter the results
     * @return the found list of activities
     * @throws MailerSendException
     */
    public Activity[] getActivities(String domainId, int page, int limit, LocalDateTime dateFrom, LocalDateTime dateTo, String[] events) throws MailerSendException {
        
        String endpoint = "/activity/".concat(domainId);
        
        if (page > -1) {
            
            endpoint = endpoint.concat("&page=").concat(String.valueOf(page));
        }
        
        if (limit > -1) {
            
            endpoint = endpoint.concat("&limit=").concat(String.valueOf(limit));
        }
        

        // TODO: add the rest of the query params (dateFrom, dateTo, etc.).
        
        MailerSendApi api = new MailerSendApi();
        api.setToken(this.token);
        
        Activity[] activities = api.getRequest(endpoint, Activity[].class);
        
        return activities;
    }
}
