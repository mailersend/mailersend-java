/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.sms.activities;

import com.google.gson.annotations.SerializedName;
import com.mailersend.sdk.util.PaginatedResponse;

/**
 * <p>SmsActivityList class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class SmsActivityList extends PaginatedResponse {

	@SerializedName("data")
	public SmsActivity[] smsActivities;
	
	/**
	 * <p>postDeserialize.</p>
	 */
	public void postDeserialize() {
		for (SmsActivity a : smsActivities) {
			a.postDeserialize();
		}
	}
}
