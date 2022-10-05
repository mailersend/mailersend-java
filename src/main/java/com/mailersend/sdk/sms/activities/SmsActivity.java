/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.sms.activities;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

import com.google.gson.annotations.SerializedName;

/**
 * <p>SmsActivity class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class SmsActivity {

	@SerializedName("from")
	public String from;
	
	@SerializedName("to")
	public String to;
	
	@SerializedName("content")
	public String content;
	
	@SerializedName("created_at")
	private String createdAtStr;
	
	public Date createdAt;
	
	@SerializedName("status")
	public String status;
	
	@SerializedName("sms_message_id")
	public String smsMessageId;
	
	/**
	 * <p>postDeserialize.</p>
	 */
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
