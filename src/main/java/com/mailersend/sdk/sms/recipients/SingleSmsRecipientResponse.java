/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.sms.recipients;

import com.google.gson.annotations.SerializedName;
import com.mailersend.sdk.MailerSendResponse;

/**
 * <p>SingleSmsRecipientResponse class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class SingleSmsRecipientResponse extends MailerSendResponse {

	@SerializedName("data")
	public SmsRecipient recipient;
}
