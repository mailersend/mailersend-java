package com.mailersend.sdk.inboundroutes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendApi;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.util.JsonSerializationDeserializationStrategy;

/**
 * <p>InboundRouteBuilder class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class InboundRouteBuilder {

	private MailerSend apiObjectReference;
	private InboundRouteBuilderBody builderBody;
	
	/**
	 * <p>Constructor for InboundRouteBuilder.</p>
	 *
	 * @param ref a {@link com.mailersend.sdk.MailerSend} object.
	 */
	public InboundRouteBuilder(MailerSend ref) {
		apiObjectReference = ref;
		builderBody = new InboundRouteBuilderBody();
	}
	
	/**
	 * <p>domainId.</p>
	 *
	 * @param domainId a {@link java.lang.String} object.
	 * @return a {@link com.mailersend.sdk.inboundroutes.InboundRouteBuilder} object.
	 */
	public InboundRouteBuilder domainId(String domainId) {
		builderBody.domainId = domainId;
		return this;
	}

	/**
	 * <p>name.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 * @return a {@link com.mailersend.sdk.inboundroutes.InboundRouteBuilder} object.
	 */
	public InboundRouteBuilder name(String name) {
		
		builderBody.name = name;
		
		return this;
	}
	
	/**
	 * <p>domainEnabled.</p>
	 *
	 * @param enabled a boolean.
	 * @return a {@link com.mailersend.sdk.inboundroutes.InboundRouteBuilder} object.
	 */
	public InboundRouteBuilder domainEnabled(boolean enabled) {
		builderBody.domainEnabled = enabled;
		
		return this;
	}
	
	/**
	 * <p>inboundDomain.</p>
	 *
	 * @param domain a {@link java.lang.String} object.
	 * @return a {@link com.mailersend.sdk.inboundroutes.InboundRouteBuilder} object.
	 */
	public InboundRouteBuilder inboundDomain(String domain) {
		builderBody.inboundDomain= domain;
		return this;
	}
	
	/**
	 * <p>inboundAddress.</p>
	 *
	 * @param address a {@link java.lang.String} object.
	 * @return a {@link com.mailersend.sdk.inboundroutes.InboundRouteBuilder} object.
	 */
	public InboundRouteBuilder inboundAddress(String address) {
		builderBody.inboundAddress = address;
		return this;
	}
	
	/**
	 * <p>inboundSubdomain.</p>
	 *
	 * @param subdomain a {@link java.lang.String} object.
	 * @return a {@link com.mailersend.sdk.inboundroutes.InboundRouteBuilder} object.
	 */
	public InboundRouteBuilder inboundSubdomain(String subdomain) {
		builderBody.inboundSubdomain = subdomain;
		
		return this;
	}

	/**
	 * <p>inboundPriority.</p>
	 *
	 * @param priority a int.
	 * @return a {@link com.mailersend.sdk.inboundroutes.InboundRouteBuilder} object.
	 */
	public InboundRouteBuilder inboundPriority(int priority) {
		builderBody.inboundPriority = priority;
		return this;
	}
	
	/**
	 * <p>matchFilter.</p>
	 *
	 * @param type a {@link java.lang.String} object.
	 * @return a {@link com.mailersend.sdk.inboundroutes.InboundRouteBuilder} object.
	 */
	public InboundRouteBuilder matchFilter(String type) {
		MatchFilter filter = new MatchFilter();
		filter.type = type;
		builderBody.matchFilter = filter;
		return this;
	}
	
	/**
	 * <p>catchFilter.</p>
	 *
	 * @param filter a {@link com.mailersend.sdk.inboundroutes.CatchFilter} object.
	 * @return a {@link com.mailersend.sdk.inboundroutes.InboundRouteBuilder} object.
	 */
	public InboundRouteBuilder catchFilter(CatchFilter filter) {
		builderBody.catchFilter = filter;
		return this;
	}

	/**
	 * <p>forwards.</p>
	 *
	 * @param forwards an array of {@link com.mailersend.sdk.inboundroutes.Forward} objects.
	 * @return a {@link com.mailersend.sdk.inboundroutes.InboundRouteBuilder} object.
	 */
	public InboundRouteBuilder forwards(Forward[] forwards) {
		builderBody.forwards = forwards;
		return this;
	}
	
	
	/**
	 * <p>addRoute.</p>
	 *
	 * @return a {@link com.mailersend.sdk.inboundroutes.InboundRoute} object.
	 * @throws com.mailersend.sdk.exceptions.MailerSendException if any.
	 */
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
	
	/**
	 * <p>updateRoute.</p>
	 *
	 * @param inboundRouteId a {@link java.lang.String} object.
	 * @return a {@link com.mailersend.sdk.inboundroutes.InboundRoute} object.
	 * @throws com.mailersend.sdk.exceptions.MailerSendException if any.
	 */
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
