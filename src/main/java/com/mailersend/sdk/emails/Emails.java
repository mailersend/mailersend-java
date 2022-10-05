/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.emails;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendApi;
import com.mailersend.sdk.MailerSendResponse;
import com.mailersend.sdk.MailerSendStringResponse;
import com.mailersend.sdk.Recipient;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.util.JsonSerializationDeserializationStrategy;

public class Emails {

    private MailerSend apiObjectReference;
   
    private Recipient defaultFrom = null;
    
    public Emails(MailerSend objectRef) {
        
        apiObjectReference = objectRef;
    }
    
    
    /**
     * Sets the default from
     * @param from
     */
    public void setDefaultFrom(Recipient from) {
        
        this.defaultFrom = from;
    }
    
    
    /**
     * Creates a new email
     * @return
     */
    public Email createEmail() {
        
        Email newEmail = new Email();
        newEmail.from = this.defaultFrom;
        
        return newEmail;
    }
    
    
    /**
     * Sends the given email
     * @param email
     * @throws MailerSendException 
     */
    public MailerSendResponse send(Email email) throws MailerSendException {
        
        String json = email.serializeForSending();
        
        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());
       
        MailerSendResponse response = api.postRequest("/email", json, MailerSendResponse.class);
        
        return response;
    }
    
    
    /**
     * Sends the given emails in one batch call
     * @param emails
     * @return
     * @throws MailerSendException
     */
    public String bulkSend(Email[] emails) throws MailerSendException {
        
        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());
        
        for (Email email : emails) {
            
            email.preparePersonalizationForAllRecipients();
            email.prepareSubstitutionsForAllRecipients();
        }
        
        Gson gson = new GsonBuilder()
                .addSerializationExclusionStrategy(new JsonSerializationDeserializationStrategy(false))
                .addDeserializationExclusionStrategy(new JsonSerializationDeserializationStrategy(true))
                .create();
        
        String json = gson.toJson(emails);
        
        SendBulkResponse response = api.postRequest("/bulk-email", json, SendBulkResponse.class);
        
        return response.bulkSendId;
    }
    
    
    /**
     * Get the status of a bulk email send
     * @param bulkSendId
     * @return
     * @throws MailerSendException
     */
    public BulkSendStatus bulkSendStatus(String bulkSendId) throws MailerSendException {
        
        String endpoint = "/bulk-email/".concat(bulkSendId);
        
        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());
    
        
        MailerSendStringResponse response = api.getRequest(endpoint, MailerSendStringResponse.class);
        
        // because the response might include null fields, we'll use a custom deserializer to get the BulkSendStatus object
   
        JsonDeserializer<BulkSendStatus> deserializer = new JsonDeserializer<BulkSendStatus>() {  

            @Override
            public BulkSendStatus deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                    throws JsonParseException {
                
                JsonObject jsonObject = json.getAsJsonObject();
                
                JsonObject data = jsonObject.get("data").getAsJsonObject();
                
                BulkSendStatus newStatus = new BulkSendStatus();
                
                newStatus.id = data.get("id").getAsString();
                
                newStatus.state = data.get("state").getAsString();
                
                newStatus.totalRecipientsCount = data.get("total_recipients_count").getAsInt();
                
                newStatus.suppressedRecipientsCount = data.get("suppressed_recipients_count").getAsInt();
                
                newStatus.validationErrorsCount = data.get("validation_errors_count").getAsInt();
                
                ArrayList<String> messagesIdsList = new ArrayList<String>();
                
                if (!data.get("messages_id").isJsonNull()) {
                	
	                JsonArray messagesIds = data.get("messages_id").getAsJsonArray();
	             	                
	                for (JsonElement messageId : messagesIds) {
	                    
	                    messagesIdsList.add(messageId.getAsString());
	                }
                }
                
                newStatus.messagesId = messagesIdsList.toArray(new String[0]);
                
                newStatus.createdAtString = data.get("created_at").getAsString();
                
                newStatus.updatedAtString = data.get("updated_at").getAsString();
                
                JsonElement validationErrorsEl = data.get("validataion_errors"); 
                
                if (validationErrorsEl != null) {
                
                    newStatus.validationErrors = validationErrorsEl.getAsJsonObject();
                }
                
                JsonElement suppressedRecipientsEl = data.get("suppressed_recipients");
                
                if (suppressedRecipientsEl != null && !suppressedRecipientsEl.isJsonNull()) {
                    
                    newStatus.suppressedRecipients = suppressedRecipientsEl.getAsJsonObject();
                }
                
                return newStatus;
            }
        };
        
        GsonBuilder gsonBuilder = new GsonBuilder();
        
        gsonBuilder.registerTypeAdapter(BulkSendStatus.class, deserializer);
        
        Gson customGson = gsonBuilder.create();  
        
        BulkSendStatus status = customGson.fromJson(response.responseString, BulkSendStatus.class);
        
        status.parseDates();
        
        return status;
        
    }
}
