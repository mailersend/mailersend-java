package com.mailersend.sdk.scheduledmessages;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

import com.google.gson.annotations.SerializedName;

public class ScheduledMessage {

	@SerializedName("message_id")
	public String messageId;
	
	@SerializedName("subject")
	public String subject;
	
	@SerializedName("send_at")
	private String sendAtStr;
	
	public Date sendAt;
	
	@SerializedName("status")
	public String status;
	
	@SerializedName("status_message")
	public String statusMessage;
	
	@SerializedName("created_at")
	private String createdAtStr;
	
	public Date createdAt;
	
	@SerializedName("message")
	public Message message;
	
	@SerializedName("domain")
	public Domain domain;
	
	
    /**
     * Is called to perform any actions after the deserialization of the response
     * Do not call directly
     */
    public void postDeserialize() {
        
        parseDates();
        if (message != null) {
        	message.postDeserialize();
        }
        
        if (domain != null) {
        	domain.postDeserialize();
        }
    }
    
    
    /**
     * Parses the string dates from the response into java.util.Date objects
     */
    private void parseDates() {
        
        TemporalAccessor ta;
        Instant instant;
        
        if (createdAtStr != null && !createdAtStr.isBlank()) {
            
            ta = DateTimeFormatter.ISO_INSTANT.parse(createdAtStr);
            instant = Instant.from(ta);
            createdAt = Date.from(instant);
        }
        
        if (sendAtStr != null && !sendAtStr.isBlank()) {
            
            ta = DateTimeFormatter.ISO_INSTANT.parse(sendAtStr);
            instant = Instant.from(ta);
            sendAt = Date.from(instant);
        }
    }
}
