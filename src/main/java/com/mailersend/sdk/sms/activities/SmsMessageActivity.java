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
import com.mailersend.sdk.sms.SmsInfo;

/**
 * <p>SmsMessageActivity class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class SmsMessageActivity {

	@SerializedName("id")
	public String id;
	
	@SerializedName("from")
	public String from;
	
	@SerializedName("to")
	public String[] to;
	
	@SerializedName("text")
	public String text;
	
	@SerializedName("created_at")
	private String createdAtStr;
	
	public Date createdAt;
	
	@SerializedName("sms")
	public SmsInfo[] sms;
	
	@SerializedName("sms_activity")
	public SmsActivity[] smsActivity;
	
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
		
		for (SmsActivity a : smsActivity) {
			a.postDeserialize();
		}
		
		for (SmsInfo s : sms) {
			s.postDeserialize();
		}
	}
}
