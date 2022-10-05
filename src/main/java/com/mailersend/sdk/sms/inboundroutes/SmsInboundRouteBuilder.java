package com.mailersend.sdk.sms.inboundroutes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendApi;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.util.JsonSerializationDeserializationStrategy;

public class SmsInboundRouteBuilder {

	private MailerSend apiObjectReference;
	private SmsInboundRouteBuilderBody builderBody;
	
	public SmsInboundRouteBuilder(MailerSend ref) {
		apiObjectReference = ref;
		builderBody = new SmsInboundRouteBuilderBody();
	}

	public SmsInboundRouteBuilder smsNumberId(String smsNumberId) {
		builderBody.smsNumberId = smsNumberId;
		return this;
	}
	
	public SmsInboundRouteBuilder name(String name) {
		builderBody.name = name;
		return this;
	}
	
	public SmsInboundRouteBuilder forwardUrl(String url) {
		builderBody.forwardUrl = url;
		return this;
	}
	
	public SmsInboundRouteBuilder filter(String comparer, String value) {
		
		Filter filter = new Filter();
		filter.comparer = comparer;
		filter.value = value;
		
		builderBody.filter = filter;
		
		return this;
	}
	
	public SmsInboundRouteBuilder enabled(boolean enabled) {
		builderBody.enabled = enabled;
		return this;
	}
	
	public SmsInboundRoute addSmsInboundRoute() throws MailerSendException {
		
		String endpoint = "/sms-inbounds";

        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());
        
        Gson gson = new GsonBuilder()
                .addSerializationExclusionStrategy(new JsonSerializationDeserializationStrategy(false))
                .addDeserializationExclusionStrategy(new JsonSerializationDeserializationStrategy(true))
                .create();
        
        String json = gson.toJson(builderBody);
        
        builderBody = new SmsInboundRouteBuilderBody();
        
        SingleSmsInboundRouteResponse response = api.postRequest(endpoint, json, SingleSmsInboundRouteResponse.class);
        
        response.route.postDeserialize();
        
        return response.route;
	}
	
	public SmsInboundRoute updateSmsInboundRoute(String smsInboundRouteId) throws MailerSendException {
		
		String endpoint = "/sms-inbounds/".concat(smsInboundRouteId);
		
        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());
        
        Gson gson = new GsonBuilder()
                .addSerializationExclusionStrategy(new JsonSerializationDeserializationStrategy(false))
                .addDeserializationExclusionStrategy(new JsonSerializationDeserializationStrategy(true))
                .create();
        
        String json = gson.toJson(builderBody);
        
        builderBody = new SmsInboundRouteBuilderBody();
        
        SingleSmsInboundRouteResponse response = api.putRequest(endpoint, json, SingleSmsInboundRouteResponse.class);
        
        response.route.postDeserialize();
        
        return response.route;
	}
}
