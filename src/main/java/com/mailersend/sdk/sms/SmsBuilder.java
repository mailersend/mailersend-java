/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.sms;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendApi;
import com.mailersend.sdk.MailerSendResponse;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.util.JsonSerializationDeserializationStrategy;

/**
 * <p>SmsBuilder class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class SmsBuilder {

	private SmsBuilderBody builderBody;
	private MailerSend apiObjectReference;
	
	/**
	 * <p>Constructor for SmsBuilder.</p>
	 *
	 * @param ref a {@link com.mailersend.sdk.MailerSend} object.
	 */
	public SmsBuilder(MailerSend ref) {
		
		apiObjectReference = ref;
		builderBody = new SmsBuilderBody();
	}
	
	/**
	 * <p>addPersonalization.</p>
	 *
	 * @param phoneNumber a {@link java.lang.String} object.
	 * @param name a {@link java.lang.String} object.
	 * @param value a {@link java.lang.Object} object.
	 * @return a {@link com.mailersend.sdk.sms.SmsBuilder} object.
	 */
	public SmsBuilder addPersonalization(String phoneNumber, String name, Object value) {
		
		SmsPersonalization entry = null;
		
		for (SmsPersonalization p : builderBody.personalization) {
			
			if (p.phoneNumber.equals(phoneNumber)) {
				
				entry = p;
				break;
			}
		}
		
		if (entry != null) {
			
			entry.data.put(name, value);
		} else {
			
			entry = new SmsPersonalization();
			entry.phoneNumber = phoneNumber;
			entry.data.put(name, value);
			builderBody.personalization.add(entry);
		}
		
		return this;
	}
	
	/**
	 * <p>from.</p>
	 *
	 * @param from a {@link java.lang.String} object.
	 * @return a {@link com.mailersend.sdk.sms.SmsBuilder} object.
	 */
	public SmsBuilder from(String from) {
		
		builderBody.from = from;
		return this;
	}
	
	/**
	 * <p>text.</p>
	 *
	 * @param text a {@link java.lang.String} object.
	 * @return a {@link com.mailersend.sdk.sms.SmsBuilder} object.
	 */
	public SmsBuilder text(String text) {
		builderBody.text = text;
		
		return this;
	}
	
	/**
	 * <p>addRecipient.</p>
	 *
	 * @param phoneNumber a {@link java.lang.String} object.
	 * @return a {@link com.mailersend.sdk.sms.SmsBuilder} object.
	 */
	public SmsBuilder addRecipient(String phoneNumber) {
		
		builderBody.to.add(phoneNumber);
		
		return this;
	}
	
	/**
	 * <p>send.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 * @throws com.mailersend.sdk.exceptions.MailerSendException if any.
	 */
	public String send() throws MailerSendException {
		
		String endpoint = "/sms";
		
        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());
        
        Gson gson = new GsonBuilder()
                .addSerializationExclusionStrategy(new JsonSerializationDeserializationStrategy(false))
                .addDeserializationExclusionStrategy(new JsonSerializationDeserializationStrategy(true))
                .create();
        
        String json = gson.toJson(builderBody);
        
        builderBody = new SmsBuilderBody();
       
        MailerSendResponse response = api.postRequest(endpoint, json, MailerSendResponse.class);
        
        String messageId = null;
        
        for (Entry<String, List<String>> entry : response.headers.entrySet()) {
        	
        	if (entry.getKey().equals("x-sms-message-id")) {
        		
        		messageId = entry.getValue().get(0);
        		
        		break;
        	}
        }
        
        return messageId;
	}
}
