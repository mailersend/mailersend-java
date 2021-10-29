/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.recipients;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendApi;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.util.JsonSerializationDeserializationStrategy;

public class SuppressionAddBuilder {

    private MailerSend apiObjectReference;
    
    BlocklistAddBody blocklistRequestBody = new BlocklistAddBody();
    SuppressionAddBody suppressionAddBody = new SuppressionAddBody();
    
    /**
     * No instantiation from outside the sdk
     * @param apiObjectRef
     */
    protected SuppressionAddBuilder(MailerSend apiObjectRef) {
        
        apiObjectReference = apiObjectRef;
    }
    
    
    /**
     * Add a recipient to the blocklist
     * @param recipient
     */
    public void recipient(String recipient) {
        
        blocklistRequestBody.recipients.add(recipient);
        suppressionAddBody.recipients.add(recipient);
    }
    
    
    /**
     * Add a pattern to the blocklist
     * @param pattern
     */
    public void pattern(String pattern) {
        
        blocklistRequestBody.patterns.add(pattern);
    }
    
    
    /**
     * Set the domain id
     * @param domainId
     */
    public void domainId(String domainId) {
        
        blocklistRequestBody.domainId = domainId;
        suppressionAddBody.domainId = domainId;
    }
    
    
    /**
     * Adds recipients or patterns into the blocklist
     * @return
     * @throws MailerSendException
     */
    public BlocklistItem[] addToBlocklist() throws MailerSendException {
        
        if (blocklistRequestBody.patterns.size() == 0 && blocklistRequestBody.recipients.size() == 0) {
            
            throw new MailerSendException("No patterns or recipients specified for blocklist");
        }
        
        String endpoint = "/suppressions/blocklist";
        
        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());
        
        Gson gson = new GsonBuilder()
                .addSerializationExclusionStrategy(new JsonSerializationDeserializationStrategy(false))
                .addDeserializationExclusionStrategy(new JsonSerializationDeserializationStrategy(true))
                .create();
        
        String json = gson.toJson(blocklistRequestBody);
        
        // reset the body object's values so that it can be reused
        blocklistRequestBody.reset();
        suppressionAddBody.reset();
        
        BlocklistListResponse response = api.postRequest(endpoint, json, BlocklistListResponse.class);
        
        return response.items;
    }
    
    
    /**
     * Adds recipients to the hard bounces suppression list
     * @return
     * @throws MailerSendException
     */
    public SuppressionList addRecipientsToHardBounces() throws MailerSendException {
        
        return this.addRecipientsToSuppressionList("/suppressions/hard-bounces");
    }
    
    
    /**
     * Adds recipients to the spam complaints suppression list
     * @return
     * @throws MailerSendException
     */
    public SuppressionList addRecipientsToSpamComplaints() throws MailerSendException {
        
        return this.addRecipientsToSuppressionList("/suppressions/spam-complaints");
    }
    
    
    /**
     * Adds recipients to the unsubscribes suppression list
     * @return
     * @throws MailerSendException
     */
    public SuppressionList addRecipientsToUnsubscribes() throws MailerSendException {
        
        return this.addRecipientsToSuppressionList("/suppressions/unsubscribes");
    }
    
    
    /**
     * 
     * @param endpoint
     * @return
     * @throws MailerSendException
     */
    private SuppressionList addRecipientsToSuppressionList(String endpoint) throws MailerSendException {
        
        if (suppressionAddBody.recipients.size() == 0) {
            
            throw new MailerSendException("No recipients specified for suppression list");
        }
        
        if (suppressionAddBody.domainId == null) {
            
            throw new MailerSendException("No domain id specified");
        }
        
        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());
        
        Gson gson = new GsonBuilder()
                .addSerializationExclusionStrategy(new JsonSerializationDeserializationStrategy(false))
                .addDeserializationExclusionStrategy(new JsonSerializationDeserializationStrategy(true))
                .create();
        
        String json = gson.toJson(suppressionAddBody);
        
        // reset the body object's values so that it can be reused
        blocklistRequestBody.reset();
        suppressionAddBody.reset();
        
        SuppressionList response = api.postRequest(endpoint, json, SuppressionList.class);
        
        return response;
    }
    
    
}
