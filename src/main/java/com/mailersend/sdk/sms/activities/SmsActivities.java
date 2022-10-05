/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.sms.activities;

import java.util.ArrayList;
import java.util.Date;

import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendApi;
import com.mailersend.sdk.exceptions.MailerSendException;

/**
 * <p>SmsActivities class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class SmsActivities {

	private MailerSend apiObjectReference;
    
    private int pageFilter = 1;
    private int limitFilter = 25;
    public ArrayList<String> statusFilter = new ArrayList<String>();
    public String numberIdFilter;
    public Date dateFromFilter;
    public Date dateToFilter;
    
    /**
     * <p>Constructor for SmsActivities.</p>
     *
     * @param ref a {@link com.mailersend.sdk.MailerSend} object.
     */
    public SmsActivities(MailerSend ref) {
    	apiObjectReference = ref;
    }
    
    
    /**
     * <p>page.</p>
     *
     * @param page a int.
     * @return a {@link com.mailersend.sdk.sms.activities.SmsActivities} object.
     */
    public SmsActivities page(int page) {
    	pageFilter = page;
    	return this;
    }
    
    
    /**
     * <p>limit.</p>
     *
     * @param limit a int.
     * @return a {@link com.mailersend.sdk.sms.activities.SmsActivities} object.
     */
    public SmsActivities limit(int limit) {
    	limitFilter = limit;
    	return this;
    }
    
    
    /**
     * <p>addStatusFilter.</p>
     *
     * @param status a {@link java.lang.String} object.
     * @return a {@link com.mailersend.sdk.sms.activities.SmsActivities} object.
     */
    public SmsActivities addStatusFilter(String status) {
    	statusFilter.add(status);
    	return this;
    }
    
    
    /**
     * <p>clearStatusFilter.</p>
     *
     * @return a {@link com.mailersend.sdk.sms.activities.SmsActivities} object.
     */
    public SmsActivities clearStatusFilter() {
    	statusFilter.clear();
    	return this;
    }
    
    
    /**
     * <p>smsNumberId.</p>
     *
     * @param numberId a {@link java.lang.String} object.
     * @return a {@link com.mailersend.sdk.sms.activities.SmsActivities} object.
     */
    public SmsActivities smsNumberId(String numberId) {
    	numberIdFilter = numberId;
    	return this;
    }
    
    
    /**
     * <p>dateFrom.</p>
     *
     * @param from a {@link java.util.Date} object.
     * @return a {@link com.mailersend.sdk.sms.activities.SmsActivities} object.
     */
    public SmsActivities dateFrom(Date from) {
    	dateFromFilter = from;
    	return this;
    }
    
    
    /**
     * <p>dateTo.</p>
     *
     * @param to a {@link java.util.Date} object.
     * @return a {@link com.mailersend.sdk.sms.activities.SmsActivities} object.
     */
    public SmsActivities dateTo(Date to) {
    	dateToFilter = to;
    	return this;
    }
    
    
    /**
     * <p>getActivities.</p>
     *
     * @return a {@link com.mailersend.sdk.sms.activities.SmsActivityList} object.
     * @throws com.mailersend.sdk.exceptions.MailerSendException if any.
     */
    public SmsActivityList getActivities() throws MailerSendException {
    	
    	String endpoint = "/sms-activity".concat(prepareParamsUrl());
    	
        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());
        
        SmsActivityList response = api.getRequest(endpoint, SmsActivityList.class);
        response.postDeserialize();
        
        return response;
    }
    
    
    /**
     * <p>getMessageActivity.</p>
     *
     * @param messageId a {@link java.lang.String} object.
     * @return a {@link com.mailersend.sdk.sms.activities.SmsMessageActivity} object.
     * @throws com.mailersend.sdk.exceptions.MailerSendException if any.
     */
    public SmsMessageActivity getMessageActivity(String messageId) throws MailerSendException {
    	
    	String endpoint = "/sms-activity/".concat(messageId);
    	
        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());
        
        SingleSmsMessageActivityResponse response = api.getRequest(endpoint, SingleSmsMessageActivityResponse.class);
        
        response.activity.postDeserialize();
        
        return response.activity;
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
        
        if (statusFilter.size() > 0) {
        	for (String f : statusFilter) {
        		params.add("status[]=".concat(f));
        	}
        }
        
        if (numberIdFilter != null) {
        	params.add("sms_number_id=".concat(numberIdFilter));
        }
        
        if (dateFromFilter != null) {
        	params.add("date_from=".concat(String.valueOf(dateFromFilter.getTime() / 1000)));
        }
        
        if (dateToFilter != null) {
        	params.add("date_to=".concat(String.valueOf(dateToFilter.getTime() / 1000)));
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
