/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.sms.recipients;

import com.google.gson.annotations.SerializedName;
import com.mailersend.sdk.util.PaginatedResponse;

/**
 * <p>SmsRecipientList class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class SmsRecipientList extends PaginatedResponse {

	@SerializedName("data")
	public SmsRecipient[] recipients;
	
	/**
	 * <p>postDeserialize.</p>
	 */
	public void postDeserialize() {
		for (SmsRecipient r : recipients) {
			r.postDeserialize();
		}
	}
}
