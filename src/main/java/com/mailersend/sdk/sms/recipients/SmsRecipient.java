/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.sms.recipients;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

import com.google.gson.annotations.SerializedName;
import com.mailersend.sdk.sms.SmsInfo;

/**
 * <p>SmsRecipient class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class SmsRecipient {

	@SerializedName("id")
	public String id;
	
	@SerializedName("number")
	public String number;
	
	@SerializedName("status")
	public String status;
	
	@SerializedName("created_at")
	private String createdAtStr;
	
	public Date createdAt;
	
	@SerializedName("sms")
	public SmsInfo[] sms;
	
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
		
		if (sms != null) {
			for (SmsInfo s : sms) {
				s.postDeserialize();
			}
		}
	}
}
