/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.analytics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendApi;
import com.mailersend.sdk.exceptions.MailerSendException;

/**
 * <p>Analytics class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class Analytics {

    // we need a reference to the MailerSend object to get the token and pass it to the responses
    private MailerSend apiObjectReference;
    
    // we are using the builder pattern so we keep the request options as private properties along with their default values
    private String domainIdFilter = null;
    
    private Date dateFromFilter = null;
    private Date dateToFilter = null;
    
    private ArrayList<String> tagsFilter = new ArrayList<String>();
       
    
    /**
     * Do not initialize directly. This should only be accessed from MailerSend.analytics
     *
     * @param ref a {@link com.mailersend.sdk.MailerSend} object.
     */
    public Analytics(MailerSend ref) {
        
        apiObjectReference = ref;
    }
    
    
    /**
     * Sets the domain id for the request
     *
     * @param domainId a {@link java.lang.String} object.
     * @return a {@link com.mailersend.sdk.analytics.Analytics} object.
     */
    public Analytics domainId(String domainId) {
        
        this.domainIdFilter = domainId;
        
        return this;
    }
    
    
    /**
     * Sets the date from filter for the request
     *
     * @param dateFrom a {@link java.util.Date} object.
     * @return a {@link com.mailersend.sdk.analytics.Analytics} object.
     */
    public Analytics dateFrom(Date dateFrom) {
        
        this.dateFromFilter = dateFrom;
        
        return this;
    }
    
    
    /**
     * Sets the date to filter for the request
     *
     * @param dateTo a {@link java.util.Date} object.
     * @return a {@link com.mailersend.sdk.analytics.Analytics} object.
     */
    public Analytics dateTo(Date dateTo) {
        
        this.dateToFilter = dateTo;
        
        return this;
    }
    
    
    /**
     * Sets the tags filters for the request
     *
     * @param tags an array of {@link java.lang.String} objects.
     * @return a {@link com.mailersend.sdk.analytics.Analytics} object.
     */
    public Analytics tags(String[] tags) {
        
        this.tagsFilter.clear();
        this.tagsFilter.addAll(Arrays.asList(tags));
        
        return this;
    }
    
    
    /**
     * Adds a tag filter to the request
     *
     * @param tag a {@link java.lang.String} object.
     * @return a {@link com.mailersend.sdk.analytics.Analytics} object.
     */
    public Analytics tag(String tag) {
        
        this.tagsFilter.add(tag);
        
        return this;
    }
    
    
    /**
     * Gets the analytics by date
     *
     * @param events an array of {@link java.lang.String} objects.
     * @throws com.mailersend.sdk.exceptions.MailerSendException
     * @return a {@link com.mailersend.sdk.analytics.AnalyticsByDateList} object.
     */
    public AnalyticsByDateList getByDate(String[] events) throws MailerSendException {
        
        return this.getByDate("group_by=days", events);
    }
    
    
    /**
     * Gets the analytics by date
     *
     * @param groupBy a {@link java.lang.String} object.
     * @param events an array of {@link java.lang.String} objects.
     * @throws com.mailersend.sdk.exceptions.MailerSendException
     * @return a {@link com.mailersend.sdk.analytics.AnalyticsByDateList} object.
     */
    public AnalyticsByDateList getByDate(String groupBy, String[] events) throws MailerSendException {
        
        if (dateFromFilter == null || dateToFilter == null) {
            
            throw new MailerSendException("Date from and Date to dates are required.");
        }
        
        if (events == null || events.length == 0) {
            
            throw new MailerSendException("No events passed.");
        }
        
        String endpoint = "/analytics/date";
        
        ArrayList<String> params = new ArrayList<String>();
        
        params.add(groupBy);
        
        if (events != null) {
            
            params.add(arrayToUrlRequest(events, "event"));
        }
        
        endpoint = endpoint.concat(prepareParamsUrl(params.toArray(new String[0])));
        
        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());
        
        AnalyticsByDateResponse msResponse = api.getRequest(endpoint, AnalyticsByDateResponse.class);
        
        msResponse.postDeserialize();
        
        return msResponse.response;
    }
    
    
    /**
     * Gets the opens by country
     *
     * @throws com.mailersend.sdk.exceptions.MailerSendException
     * @return a {@link com.mailersend.sdk.analytics.AnalyticsList} object.
     */
    public AnalyticsList getOpensByCountry() throws MailerSendException {
        
        return genericAnalyticsRequest("/analytics/country");
    }
    
    
    /**
     * Gets the opens by user agent
     *
     * @throws com.mailersend.sdk.exceptions.MailerSendException
     * @return a {@link com.mailersend.sdk.analytics.AnalyticsList} object.
     */
    public AnalyticsList getOpensByUserAgent() throws MailerSendException {
        
        return genericAnalyticsRequest("/analytics/ua-name");
    }
    
    
    /**
     * Gets the opens by the user agent type (by reading environment)
     *
     * @throws com.mailersend.sdk.exceptions.MailerSendException
     * @return a {@link com.mailersend.sdk.analytics.AnalyticsList} object.
     */
    public AnalyticsList getOpensByUserAgenType() throws MailerSendException {
        
        return genericAnalyticsRequest("/analytics/ua-type");
    }
    
    
    /**
     * Implementation for all analytics requests except the getByDate ones
     * @param requestEndpoint
     * @return
     * @throws MailerSendException
     */
    private AnalyticsList genericAnalyticsRequest(String requestEndpoint) throws MailerSendException {
        
        String endpoint = requestEndpoint.concat(prepareParamsUrl(null));
        
        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());
        
        AnalyticsResponse msResponse = api.getRequest(endpoint, AnalyticsResponse.class);
        
        msResponse.postDeserialize();
        
        return msResponse.response;
    }
    
    
    /**
     * Prepares the query part of the request url
     * @param additionalParams pass additional params in the form of name=value to include to the request url
     * @return
     */
    private String prepareParamsUrl(String[] additionalParams) {
        
        // prepare the request parameters
        ArrayList<String> params = new ArrayList<String>();
        
        if (additionalParams != null) {
        
            params.addAll(Arrays.asList(additionalParams));
        }
        
        if (domainIdFilter != null) {
            
            params.add("domain_id=".concat(domainIdFilter));
        }
        
        if (dateFromFilter != null) {
            
            params.add("date_from=".concat(String.valueOf(dateFromFilter.getTime() / 1000)));
        }
        
        if (dateToFilter != null) {
            
            params.add("date_to=".concat(String.valueOf(dateToFilter.getTime() / 1000)));
        }
        
        if (tagsFilter.size() > 0) {
            
            params.add(arrayToUrlRequest(tagsFilter.toArray(new String[0]), "tags"));
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
    
    
    /**
     * Builds a GET array query for the values with name field. E.g. field[]=value1&field[]=value2
     * @param values
     * @param field
     * @return
     */
    private String arrayToUrlRequest(String[] values, String field) {
        
        if (values == null || field == null) {
            
            return "";
        }
        
        String params = "";
        
        for (int i = 0; i < values.length; i++) {
            
            if (i > 0) {
                
                params = params.concat("&");
            }
            
            params = params.concat(field).concat("[]=").concat(values[i]);
        }
        
        return params;
    }
    
}
