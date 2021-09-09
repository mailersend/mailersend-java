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
import java.util.ArrayList;
import java.util.Date;

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
       
        MailerSendResponse response = api.postRequest("/email", json, MailerSendResponse.class);
        
        return response;
    }
    
    
    /**
     * Gets the activities for the given domain id
     * @param domainId
     * @return 
     * @throws MailerSendException
     */
    public Activities getActivities(String domainId) throws MailerSendException {
        
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
    public Activities getActivities(String domainId, int page, int limit, Date dateFrom, Date dateTo, String[] events) throws MailerSendException {
        
        String endpoint = "/activity/".concat(domainId);
        
        // prepare the request parameters
        ArrayList<String> params = new ArrayList<String>();
        
        if (page > -1) {
            
            params.add("page=".concat(String.valueOf(page)));
        }
        
        if (limit > -1) {
            
            params.add("limit=".concat(String.valueOf(limit)));
        }
        
        if (dateFrom != null && dateTo != null) {
            
            if (!dateTo.after(dateFrom)) {
                
                throw new MailerSendException("From date cannot be after to date.");
            }
        }
        
        if (dateFrom != null) {
            
            params.add("date_from=".concat(String.valueOf(dateFrom.getTime() / 1000)));
        }
        
        if (dateTo != null) {
            
            params.add("date_to=".concat(String.valueOf(dateTo.getTime() / 1000)));
        }
        
        if (events != null) {
            
            String eventsParam = "";
            for (int i = 0; i < events.length; i++) {
                
                if (i > 0) {
                    
                    eventsParam.concat("&");
                }
                
                eventsParam = eventsParam.concat("event[]=").concat(events[i]);
            }
            
            params.add(eventsParam);
        }
        
        for (int i = 0; i < params.size(); i++) {
            
            String attrSep = "&";
            
            if (i == 0) {
                
                attrSep = "?";
            }
            
            endpoint = endpoint.concat(attrSep).concat(params.get(i));
        }

        
        MailerSendApi api = new MailerSendApi();
        api.setToken(this.token);
        
        Activities activities = api.getRequest(endpoint, Activities.class);
        
        activities.postDeserialize();
        
        activities.mailersendObj = this;
        activities.domainId = domainId;
        activities.dateFrom = dateFrom;
        activities.dateTo = dateTo;
        activities.events = events;
        
        return activities;
    }
}
