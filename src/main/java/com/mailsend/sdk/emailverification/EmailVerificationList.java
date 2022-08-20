package com.mailsend.sdk.emailverification;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

import com.google.gson.annotations.SerializedName;

public class EmailVerificationList {

	@SerializedName("id")
	public String id;
	
	@SerializedName("name")
	public String name;
	
	@SerializedName("total")
	public int total;
	
	@SerializedName("verification_started")
	private String verificationStartedStr;
	
	public Date verificationStarted;
	
	@SerializedName("verification_ended")
	private String verificationEndedStr;
	
	public Date verificationEnded;
	
	@SerializedName("created_at")
	private String createdAtStr;
	
	public Date createdAt;
	
	@SerializedName("updated_at")
	private String updatedAtStr;
	
	public Date updatedAt;
	
	@SerializedName("source")
	public String source;
	
	@SerializedName("status")
	public Status status;
	
	@SerializedName("statistics")
	public Statistics statistics;
	
	public void postDeserialize() {
		
        TemporalAccessor ta;
        Instant instant;
        
		if (verificationStartedStr != null && !verificationStartedStr.isBlank()) {
	        
            ta = DateTimeFormatter.ISO_INSTANT.parse(verificationStartedStr);
            instant = Instant.from(ta);
            verificationStarted = Date.from(instant);
		}
		
		if (verificationEndedStr != null && !verificationEndedStr.isBlank()) {
	        
            ta = DateTimeFormatter.ISO_INSTANT.parse(verificationEndedStr);
            instant = Instant.from(ta);
            verificationEnded = Date.from(instant);
		}
        
        if (createdAtStr != null && !createdAtStr.isBlank()) {
            
            ta = DateTimeFormatter.ISO_INSTANT.parse(createdAtStr);
            instant = Instant.from(ta);
            createdAt = Date.from(instant);
        }
        
        if (updatedAtStr != null && !updatedAtStr.isBlank()) {
            
            ta = DateTimeFormatter.ISO_INSTANT.parse(updatedAtStr);
            instant = Instant.from(ta);
            updatedAt = Date.from(instant);
        }
	}
}
