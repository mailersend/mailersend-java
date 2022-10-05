/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.sms.inboundroutes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendApi;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.util.JsonSerializationDeserializationStrategy;

/**
 * <p>SmsInboundRouteBuilder class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class SmsInboundRouteBuilder {

	private MailerSend apiObjectReference;
	private SmsInboundRouteBuilderBody builderBody;
	
	/**
	 * <p>Constructor for SmsInboundRouteBuilder.</p>
	 *
	 * @param ref a {@link com.mailersend.sdk.MailerSend} object.
	 */
	public SmsInboundRouteBuilder(MailerSend ref) {
		apiObjectReference = ref;
		builderBody = new SmsInboundRouteBuilderBody();
	}

	/**
	 * <p>smsNumberId.</p>
	 *
	 * @param smsNumberId a {@link java.lang.String} object.
	 * @return a {@link com.mailersend.sdk.sms.inboundroutes.SmsInboundRouteBuilder} object.
	 */
	public SmsInboundRouteBuilder smsNumberId(String smsNumberId) {
		builderBody.smsNumberId = smsNumberId;
		return this;
	}
	
	/**
	 * <p>name.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 * @return a {@link com.mailersend.sdk.sms.inboundroutes.SmsInboundRouteBuilder} object.
	 */
	public SmsInboundRouteBuilder name(String name) {
		builderBody.name = name;
		return this;
	}
	
	/**
	 * <p>forwardUrl.</p>
	 *
	 * @param url a {@link java.lang.String} object.
	 * @return a {@link com.mailersend.sdk.sms.inboundroutes.SmsInboundRouteBuilder} object.
	 */
	public SmsInboundRouteBuilder forwardUrl(String url) {
		builderBody.forwardUrl = url;
		return this;
	}
	
	/**
	 * <p>filter.</p>
	 *
	 * @param comparer a {@link java.lang.String} object.
	 * @param value a {@link java.lang.String} object.
	 * @return a {@link com.mailersend.sdk.sms.inboundroutes.SmsInboundRouteBuilder} object.
	 */
	public SmsInboundRouteBuilder filter(String comparer, String value) {
		
		Filter filter = new Filter();
		filter.comparer = comparer;
		filter.value = value;
		
		builderBody.filter = filter;
		
		return this;
	}
	
	/**
	 * <p>enabled.</p>
	 *
	 * @param enabled a boolean.
	 * @return a {@link com.mailersend.sdk.sms.inboundroutes.SmsInboundRouteBuilder} object.
	 */
	public SmsInboundRouteBuilder enabled(boolean enabled) {
		builderBody.enabled = enabled;
		return this;
	}
	
	/**
	 * <p>addSmsInboundRoute.</p>
	 *
	 * @return a {@link com.mailersend.sdk.sms.inboundroutes.SmsInboundRoute} object.
	 * @throws com.mailersend.sdk.exceptions.MailerSendException if any.
	 */
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
	
	/**
	 * <p>updateSmsInboundRoute.</p>
	 *
	 * @param smsInboundRouteId a {@link java.lang.String} object.
	 * @return a {@link com.mailersend.sdk.sms.inboundroutes.SmsInboundRoute} object.
	 * @throws com.mailersend.sdk.exceptions.MailerSendException if any.
	 */
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
