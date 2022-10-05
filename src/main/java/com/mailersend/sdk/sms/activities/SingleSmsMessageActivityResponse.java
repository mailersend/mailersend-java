/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.sms.activities;

import com.google.gson.annotations.SerializedName;
import com.mailersend.sdk.MailerSendResponse;

/**
 * <p>SingleSmsMessageActivityResponse class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class SingleSmsMessageActivityResponse extends MailerSendResponse {

	@SerializedName("data")
	public SmsMessageActivity activity;
}
