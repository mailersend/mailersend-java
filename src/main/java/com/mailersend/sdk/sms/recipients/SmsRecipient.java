/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.sms.recipients;

import java.util.Date;

import com.google.gson.annotations.SerializedName;
import com.mailersend.sdk.sms.SmsInfo;
import com.mailersend.sdk.util.DateParser;

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
	
	@SerializedName("opted_out_at")
	private String optedOutAtStr;
	
	public Date optedOutAt;
	
	@SerializedName("opt_out_source")
	public String optOutSource;
	
	@SerializedName("created_at")
	private String createdAtStr;
	
	public Date createdAt;
	
	@SerializedName("sms")
	public SmsInfo[] sms;
	
	/**
	 * <p>postDeserialize.</p>
	 */
	public void postDeserialize() {
		createdAt = DateParser.parse(createdAtStr);
		optedOutAt = DateParser.parse(optedOutAtStr);
		
		if (sms != null) {
			for (SmsInfo s : sms) {
				s.postDeserialize();
			}
		}
	}
}
