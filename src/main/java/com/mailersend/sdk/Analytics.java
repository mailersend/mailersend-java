package com.mailersend.sdk;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import com.mailersend.sdk.exceptions.MailerSendException;

public class Analytics {

    // we need a reference to the MailerSend object to get the token and pass it to the responses
    private MailerSend apiObjectReference;
    
    // we are using the builder pattern so we keep the request options as private properties along with their default values
    private String domainIdFilter = null;
    
    private Date dateFromFilter = null;
    private Date dateToFilter = null;
    
    private ArrayList<String> tagsFilter = new ArrayList<String>();
       
    
    protected Analytics(MailerSend ref) {
        
        apiObjectReference = ref;
    }
    
    
    public Analytics domainId(String domainId) {
        
        this.domainIdFilter = domainId;
        
        return this;
    }
    
    public Analytics dateFrom(Date dateFrom) {
        
        this.dateFromFilter = dateFrom;
        
        return this;
    }
    
    public Analytics dateTo(Date dateTo) {
        
        this.dateToFilter = dateTo;
        
        return this;
    }
    
    
    public Analytics tags(String[] tags) {
        
        this.tagsFilter.clear();
        this.tagsFilter.addAll(Arrays.asList(tags));
        
        return this;
    }
    
    public Analytics tag(String tag) {
        
        this.tagsFilter.add(tag);
        
        return this;
    }
    
    
    public AnalyticsByDateList getByDate() throws MailerSendException {
        
        return this.getByDate("days", null);
    }
    
    
    public AnalyticsByDateList getByDate(String groupBy, String[] events) throws MailerSendException {
        
        String endpoint = "/analytics/date";
        
        ArrayList<String> params = new ArrayList<String>();
        
        params.add(groupBy);
        
        if (events != null) {
            
            params.add(arrayToUrlRequest(events, "event"));
        }
        
        endpoint = endpoint.concat(prepareParamsUrl(params.toArray(new String[0])));
        
        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.token);
        
        AnalyticsByDateResponse msResponse = api.getRequest(endpoint, AnalyticsByDateResponse.class);
        
        msResponse.postDeserialize();
        
        return msResponse.response;
    }
    
    
    public AnalyticsList getOpensByCountry() throws MailerSendException {
        
        return genericAnalyticsRequest("analytics/country");
    }
    
    
    public AnalyticsList getOpensByUserAgent() throws MailerSendException {
        
        return genericAnalyticsRequest("analytics/ua-name");
    }
    
    
    public AnalyticsList getOpensByUserAgenType() throws MailerSendException {
        
        return genericAnalyticsRequest("analytics/ua-type");
    }
    
    
    private AnalyticsList genericAnalyticsRequest(String requestEndpoint) throws MailerSendException {
        
        String endpoint = requestEndpoint.concat(arrayToUrlRequest(null, null));
        
        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.token);
        
        AnalyticsResponse msResponse = api.getRequest(endpoint, AnalyticsResponse.class);
        
        msResponse.postDeserialize();
        
        return msResponse.response;
    }
    
    
    private String prepareParamsUrl(String[] additionalParams) {
        
        // prepare the request parameters
        ArrayList<String> params = new ArrayList<String>();
        
        params.addAll(Arrays.asList(additionalParams));
        
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
    
    
    private String arrayToUrlRequest(String[] values, String field) {
        
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
