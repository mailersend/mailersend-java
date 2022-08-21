package com.mailersend.sdk.sms;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

import com.google.gson.annotations.SerializedName;

public class SmsInfo {

	@SerializedName("id")
	public String id;
	
	@SerializedName("from")
	public String from;
	
	@SerializedName("to")
	public String to;
	
	@SerializedName("text")
	public String text;
	
	@SerializedName("status")
	public String status;
	
	@SerializedName("segment_count")
	public int segmentCount;
	
	@SerializedName("error_type")
	public String errorType;
	
	@SerializedName("error_description")
	public String errorDescription;
	
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
