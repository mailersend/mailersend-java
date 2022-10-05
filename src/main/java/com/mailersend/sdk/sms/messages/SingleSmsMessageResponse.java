/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.sms.messages;

import com.google.gson.annotations.SerializedName;
import com.mailersend.sdk.MailerSendResponse;

class SingleSmsMessageResponse extends MailerSendResponse {

	@SerializedName("data")
	public SmsMessage message;
}
