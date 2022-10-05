/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.sms.inboundroutes;

import java.util.ArrayList;
import java.util.stream.IntStream;

import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendApi;
import com.mailersend.sdk.MailerSendResponse;
import com.mailersend.sdk.exceptions.MailerSendException;

/**
 * <p>SmsInboundRoutes class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class SmsInboundRoutes {

	private MailerSend apiObjectReference;
    
	private SmsInboundRouteBuilder builder;
	
    private int pageFilter = 1;
    private int limitFilter = 25;
    private String smsNumberIdFilter;
    private Boolean enabledFilter = null;
    
    /**
     * <p>Constructor for SmsInboundRoutes.</p>
     *
     * @param ref a {@link com.mailersend.sdk.MailerSend} object.
     */
    public SmsInboundRoutes(MailerSend ref) {
    	apiObjectReference = ref;
    	builder = new SmsInboundRouteBuilder(ref);
    }
    
    /**
     * <p>builder.</p>
     *
     * @return a {@link com.mailersend.sdk.sms.inboundroutes.SmsInboundRouteBuilder} object.
     */
    public SmsInboundRouteBuilder builder() {
    	return builder;
    }
    
    /**
     * <p>newBuilder.</p>
     *
     * @return a {@link com.mailersend.sdk.sms.inboundroutes.SmsInboundRouteBuilder} object.
     */
    public SmsInboundRouteBuilder newBuilder() {
    	
    	return new SmsInboundRouteBuilder(apiObjectReference);
    }
    
    /**
     * <p>page.</p>
     *
     * @param page a int.
     * @return a {@link com.mailersend.sdk.sms.inboundroutes.SmsInboundRoutes} object.
     */
    public SmsInboundRoutes page(int page) {
    	pageFilter = page;
    	return this;
    }
    
    /**
     * <p>limit.</p>
     *
     * @param limit a int.
     * @return a {@link com.mailersend.sdk.sms.inboundroutes.SmsInboundRoutes} object.
     */
    public SmsInboundRoutes limit(int limit) {
    	limitFilter = limit;
    	return this;
    }
    
    /**
     * <p>smsNumberId.</p>
     *
     * @param smsNumberId a {@link java.lang.String} object.
     * @return a {@link com.mailersend.sdk.sms.inboundroutes.SmsInboundRoutes} object.
     */
    public SmsInboundRoutes smsNumberId(String smsNumberId) {
    	smsNumberIdFilter = smsNumberId;
    	return this;
    }
    
    /**
     * <p>enabled.</p>
     *
     * @param enabled a boolean.
     * @return a {@link com.mailersend.sdk.sms.inboundroutes.SmsInboundRoutes} object.
     */
    public SmsInboundRoutes enabled(boolean enabled) {
    	enabledFilter = enabled;
    	return this;
    }
    
    /**
     * <p>getSmsInboundRoutes.</p>
     *
     * @return a {@link com.mailersend.sdk.sms.inboundroutes.SmsInboundRouteList} object.
     * @throws com.mailersend.sdk.exceptions.MailerSendException if any.
     */
    public SmsInboundRouteList getSmsInboundRoutes() throws MailerSendException {
    	
    	String endpoint = "/sms-inbounds".concat(prepareParamsUrl());
    	
        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());
        
        SmsInboundRouteList response = api.getRequest(endpoint, SmsInboundRouteList.class);
        
        response.postDeserialize();
        
        return response;
    }
    
    /**
     * <p>getSmsInboundRoute.</p>
     *
     * @param routeId a {@link java.lang.String} object.
     * @return a {@link com.mailersend.sdk.sms.inboundroutes.SmsInboundRoute} object.
     * @throws com.mailersend.sdk.exceptions.MailerSendException if any.
     */
    public SmsInboundRoute getSmsInboundRoute(String routeId) throws MailerSendException {
        
    	String endpoint = "/sms-inbounds/".concat(routeId);
    	
        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());
        
        SingleSmsInboundRouteResponse response = api.getRequest(endpoint, SingleSmsInboundRouteResponse.class);
        
        response.route.postDeserialize();
        
        return response.route;
    }
    
    /**
     * <p>deleteSmsInboundRoute.</p>
     *
     * @param routeId a {@link java.lang.String} object.
     * @return a boolean.
     * @throws com.mailersend.sdk.exceptions.MailerSendException if any.
     */
    public boolean deleteSmsInboundRoute(String routeId) throws MailerSendException {
    	
    	String endpoint = "/sms-inbounds/".concat(routeId);
    	
        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());
        
        MailerSendResponse response = api.deleteRequest(endpoint, MailerSendResponse.class);
        
        // return true if the response is 200, 204 or 202
        return IntStream.of(new int[] {200, 204, 202}).anyMatch(x -> x == response.responseStatusCode);
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
        
        if (smsNumberIdFilter != null) {
        	params.add("sms_numnber_id=".concat(smsNumberIdFilter));
        }
        
        if (enabledFilter != null) {
        	params.add("enabled=".concat(String.valueOf(enabledFilter)));
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
