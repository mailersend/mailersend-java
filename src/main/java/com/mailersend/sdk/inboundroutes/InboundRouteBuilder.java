package com.mailersend.sdk.inboundroutes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendApi;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.util.JsonSerializationDeserializationStrategy;

public class InboundRouteBuilder {

	private MailerSend apiObjectReference;
	private InboundRouteBuilderBody builderBody;
	
	public InboundRouteBuilder(MailerSend ref) {
		apiObjectReference = ref;
		builderBody = new InboundRouteBuilderBody();
	}
	
	public InboundRouteBuilder domainId(String domainId) {
		builderBody.domainId = domainId;
		return this;
	}

	public InboundRouteBuilder name(String name) {
		
		builderBody.name = name;
		
		return this;
	}
	
	public InboundRouteBuilder domainEnabled(boolean enabled) {
		builderBody.domainEnabled = enabled;
		
		return this;
	}
	
	public InboundRouteBuilder inboundDomain(String domain) {
		builderBody.inboundDomain= domain;
		return this;
	}
	
	public InboundRouteBuilder inboundAddress(String address) {
		builderBody.inboundAddress = address;
		return this;
	}
	
	public InboundRouteBuilder inboundSubdomain(String subdomain) {
		builderBody.inboundSubdomain = subdomain;
		
		return this;
	}
	
	public InboundRouteBuilder matchFilter(String type) {
		MatchFilter filter = new MatchFilter();
		filter.type = type;
		builderBody.matchFilter = filter;
		return this;
	}
	
	public InboundRouteBuilder catchFilter(CatchFilter filter) {
		builderBody.catchFilter = filter;
		return this;
	}

	public InboundRouteBuilder forwards(Forward[] forwards) {
		builderBody.forwards = forwards;
		return this;
	}
	
	
	public InboundRoute addRoute() throws MailerSendException {
		
		String endpoint = "/inbound";

        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());
        
        Gson gson = new GsonBuilder()
                .addSerializationExclusionStrategy(new JsonSerializationDeserializationStrategy(false))
                .addDeserializationExclusionStrategy(new JsonSerializationDeserializationStrategy(true))
                .create();
        
        String json = gson.toJson(builderBody);
        
        builderBody.reset();
        
        SingleInboundRouteResponse response = api.postRequest(endpoint, json, SingleInboundRouteResponse.class);
        
        return response.route;
	}
	
	public InboundRoute updateRoute(String inboundRouteId) throws MailerSendException {
		String endpoint = "/inbound/" + inboundRouteId;
		
        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());
        
        Gson gson = new GsonBuilder()
                .addSerializationExclusionStrategy(new JsonSerializationDeserializationStrategy(false))
                .addDeserializationExclusionStrategy(new JsonSerializationDeserializationStrategy(true))
                .create();
        
        String json = gson.toJson(builderBody);
        
        builderBody.reset();
        
        SingleInboundRouteResponse response = api.putRequest(endpoint, json, SingleInboundRouteResponse.class);
        
        return response.route;
	}
}
