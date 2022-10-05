/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sms.webhooks;

import com.google.gson.annotations.SerializedName;
import com.mailersend.sdk.util.PaginatedResponse;

/**
 * <p>SmsWebhookList class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class SmsWebhookList extends PaginatedResponse {
	
	@SerializedName("data")
	public SmsWebhook[] webhooks;
	
	/**
	 * <p>postDeserialize.</p>
	 */
	protected void postDeserialize() {
		
		for (SmsWebhook webhook : webhooks) {
			
			webhook.postDeserialize();
		}
	}
}
