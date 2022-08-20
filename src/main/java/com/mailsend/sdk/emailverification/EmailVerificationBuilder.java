package com.mailsend.sdk.emailverification;

import java.util.ArrayList;
import java.util.Arrays;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendApi;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.util.JsonSerializationDeserializationStrategy;

public class EmailVerificationBuilder {

	private EmailVerificationBuilderBody builderBody;
	
	private MailerSend apiObjectReference;
	
	private ArrayList<String> emails = new ArrayList<String>();
	
	public EmailVerificationBuilder(MailerSend ref) {
		apiObjectReference = ref;
		builderBody = new EmailVerificationBuilderBody();
	}
	
	public EmailVerificationBuilder name(String name) {
		builderBody.name = name;
		return this;
	}
	
	public EmailVerificationBuilder addEmail(String email) {
		emails.add(email);
		return this;
	}
	
	public EmailVerificationBuilder addEmails(String[] emails) {
		this.emails.addAll(Arrays.asList(emails));
		return this;
	}
	
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
