package com.mailersend.sdk.inboundroutes;

import java.util.ArrayList;
import java.util.stream.IntStream;

import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendApi;
import com.mailersend.sdk.MailerSendResponse;
import com.mailersend.sdk.exceptions.MailerSendException;

public class InboundRoutes {

    private MailerSend apiObjectReference;
    
    private int pageFilter = 1;
    private int limitFilter = 25;
    private String domainIdFilter;
    
    private InboundRouteBuilder inboundRouteBuilder;
    
    public InboundRoutes(MailerSend ref) {
    	apiObjectReference = ref;
    	inboundRouteBuilder = new InboundRouteBuilder(ref);
    }
    
    public InboundRouteBuilder getNewBuilder() {
    	return new InboundRouteBuilder(apiObjectReference);
    }
    
    public InboundRouteBuilder builder() {
    	return inboundRouteBuilder;
    }
    
    public InboundRoutes page(int page) {
    	pageFilter = page;
    	return this;
    }
    
    public InboundRoutes limit(int limit) {
    	limitFilter = limit;
    	return this;
    }
    
    public InboundRoutes domainId(String domainId) {
    	domainIdFilter = domainId;
    	return this;
    }
    
    public InboundRoutesList getRoutes() throws MailerSendException {
    
    	String endpoint = "/inbound".concat(prepareParamsUrl());
    	
        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());
        
        InboundRoutesList response = api.getRequest(endpoint, InboundRoutesList.class);
        
        return response;
    }
    
    public InboundRoute getRoute(String routeId) throws MailerSendException {
    	String endpoint = "/inbound/".concat(routeId);
    	
        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());
        
        SingleInboundRouteResponse response = api.getRequest(endpoint, SingleInboundRouteResponse.class);
        
        return response.route;
    }
    
    public boolean deleteRoute(String routeId) throws MailerSendException {
    	String endpoint = "/inbound/".concat(routeId);
    	
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
