/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.activities;

import java.util.ArrayList;
import java.util.Date;

import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendApi;
import com.mailersend.sdk.exceptions.MailerSendException;

/**
 * <p>Activities class.</p>
 *
 * @author john
 * @version $Id: $Id
 */
public class Activities {

    // we need a reference to the MailerSend object to get the token and pass it to the ActivitiesList
    private MailerSend apiObjectReference;
    
    
    /**
     * Do not initialize directly. This should only be accessed from MailerSend.activities
     *
     * @param apiReference a {@link com.mailersend.sdk.MailerSend} object.
     */
    public Activities(MailerSend apiReference)
    {
        
        apiObjectReference = apiReference;
    }
    
    
    /**
     * Gets the activities for the given domain id
     *
     * @param domainId a {@link java.lang.String} object.
     * @throws com.mailersend.sdk.exceptions.MailerSendException
     * @return a {@link com.mailersend.sdk.activities.ActivitiesList} object.
     */
    public ActivitiesList getActivities(String domainId) throws MailerSendException {
        
        return this.getActivities(domainId, 1, 25, null, null, null);
    }
    
    /**
     * Gets the activities for the given domain id. Allows for pagination and filtering
     *
     * @param domainId The id of the domain to get the activities for
     * @param page The results page
     * @param limit How many results to return per page (default 25)
     * @param dateFrom The from date to filter the results
     * @param dateTo The to date to filter the results
     * @param events A list of events to filter the results
     * @return the found list of activities
     * @throws com.mailersend.sdk.exceptions.MailerSendException
     */
    public ActivitiesList getActivities(String domainId, int page, int limit, Date dateFrom, Date dateTo, String[] events) throws MailerSendException {
        
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
                    
                    eventsParam = eventsParam.concat("&");
                }
                
                eventsParam = eventsParam.concat("event[]=").concat(events[i]);
            }
            
            params.add(eventsParam);
        }
        
        // add the parameters to the endpoint url
        for (int i = 0; i < params.size(); i++) {
            
            String attrSep = "&";
            
            if (i == 0) {
                
                attrSep = "?";
            }
            
            endpoint = endpoint.concat(attrSep).concat(params.get(i));
        }

        
        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());
        
        ActivitiesList activities = api.getRequest(endpoint, ActivitiesList.class);
        
        // call postDeserialize to parse dates, etc.
        activities.postDeserialize();
        
        // we pass these to the ActivitiesList object so that we can provide pagination
        activities.mailersendObj = apiObjectReference;
        activities.domainId = domainId;
        activities.dateFrom = dateFrom;
        activities.dateTo = dateTo;
        activities.events = events;
        
        return activities;
    }
}
