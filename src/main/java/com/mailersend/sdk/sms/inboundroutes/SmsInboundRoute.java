package com.mailersend.sdk.sms.inboundroutes;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

import com.google.gson.annotations.SerializedName;
import com.mailersend.sdk.sms.phonenumbers.PhoneNumber;

public class SmsInboundRoute {

	@SerializedName("id")
	public String id;
	
	@SerializedName("name")
	public String name;
	
	@SerializedName("filter")
	public Filter filter;
	
	@SerializedName("forward_url")
	public String forwardUrl;
	
	@SerializedName("enabled")
	public boolean enabled;
	
	@SerializedName("secret")
	public String secret;
	
	public Date createdAt;
	
	@SerializedName("created_at")
	private String createdAtStr;
	
	@SerializedName("sms_number")
	public PhoneNumber smsNumber;
	
	public void postDeserialize() {
		if (createdAtStr != null && !createdAtStr.isBlank()) {
			
	        TemporalAccessor ta;
	        Instant instant;
	        
            ta = DateTimeFormatter.ISO_INSTANT.parse(createdAtStr);
            instant = Instant.from(ta);
            createdAt = Date.from(instant);
		}
		
		if (smsNumber != null) {
			
			smsNumber.postDeserialize();
		}
	}
}
