/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.sms.messages;

import com.google.gson.annotations.SerializedName;
import com.mailersend.sdk.util.PaginatedResponse;

/**
 * <p>SmsMessageList class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class SmsMessageList extends PaginatedResponse {

	@SerializedName("data")
	public SmsMessage[] messages;
	
	/**
	 * <p>postDeserialize.</p>
	 */
	public void postDeserialize() {
		for (SmsMessage m : messages) {
			m.postDeserialize();
		}
	}
}
