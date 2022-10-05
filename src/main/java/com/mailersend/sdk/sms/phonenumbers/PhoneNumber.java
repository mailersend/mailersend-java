package com.mailersend.sdk.sms.phonenumbers;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

import com.google.gson.annotations.SerializedName;

public class PhoneNumber {

	@SerializedName("id")
	public String id;
	
	@SerializedName("telephone_number")
	public String telephoneNumber;
	
	@SerializedName("paused")
	public boolean paused;
	
	@SerializedName("created_at")
	private String createdAtStr;
	
	public Date createdAt;
	
	
	public void postDeserialize() {
		if (createdAtStr != null && !createdAtStr.isBlank()) {
			
	        TemporalAccessor ta;
	        Instant instant;
	        
            ta = DateTimeFormatter.ISO_INSTANT.parse(createdAtStr);
            instant = Instant.from(ta);
            createdAt = Date.from(instant);
		}
	}
}
