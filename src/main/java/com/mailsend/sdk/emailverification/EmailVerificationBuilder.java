/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailsend.sdk.emailverification;

import java.util.ArrayList;
import java.util.Arrays;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendApi;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.util.JsonSerializationDeserializationStrategy;

/**
 * <p>EmailVerificationBuilder class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class EmailVerificationBuilder {

	private EmailVerificationBuilderBody builderBody;
	
	private MailerSend apiObjectReference;
	
	private ArrayList<String> emails = new ArrayList<String>();
	
	/**
	 * <p>Constructor for EmailVerificationBuilder.</p>
	 *
	 * @param ref a {@link com.mailersend.sdk.MailerSend} object.
	 */
	public EmailVerificationBuilder(MailerSend ref) {
		apiObjectReference = ref;
		builderBody = new EmailVerificationBuilderBody();
	}
	
	/**
	 * <p>name.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 * @return a {@link com.mailsend.sdk.emailverification.EmailVerificationBuilder} object.
	 */
	public EmailVerificationBuilder name(String name) {
		builderBody.name = name;
		return this;
	}
	
	/**
	 * <p>addEmail.</p>
	 *
	 * @param email a {@link java.lang.String} object.
	 * @return a {@link com.mailsend.sdk.emailverification.EmailVerificationBuilder} object.
	 */
	public EmailVerificationBuilder addEmail(String email) {
		emails.add(email);
		return this;
	}
	
	/**
	 * <p>addEmails.</p>
	 *
	 * @param emails an array of {@link java.lang.String} objects.
	 * @return a {@link com.mailsend.sdk.emailverification.EmailVerificationBuilder} object.
	 */
	public EmailVerificationBuilder addEmails(String[] emails) {
		this.emails.addAll(Arrays.asList(emails));
		return this;
	}
	
	/**
	 * <p>create.</p>
	 *
	 * @return a {@link com.mailsend.sdk.emailverification.EmailVerificationList} object.
	 * @throws com.mailersend.sdk.exceptions.MailerSendException if any.
	 */
	public EmailVerificationList create() throws MailerSendException {
		
		String endpoint = "/email-verification";
		
		String[] em = new String[emails.size()];
		em = emails.toArray(em);
		builderBody.emails = em;

        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());
        
        Gson gson = new GsonBuilder()
                .addSerializationExclusionStrategy(new JsonSerializationDeserializationStrategy(false))
                .addDeserializationExclusionStrategy(new JsonSerializationDeserializationStrategy(true))
                .create();
        
        String json = gson.toJson(builderBody);
        
        builderBody.reset();
        
        SingleEmailVerificationListResponse response = api.postRequest(endpoint, json, SingleEmailVerificationListResponse.class);
        
        return response.list;
	}
	
}
