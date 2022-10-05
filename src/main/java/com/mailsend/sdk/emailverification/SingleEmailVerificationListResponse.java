/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailsend.sdk.emailverification;

import com.google.gson.annotations.SerializedName;
import com.mailersend.sdk.MailerSendResponse;

/**
 * <p>SingleEmailVerificationListResponse class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class SingleEmailVerificationListResponse extends MailerSendResponse {

	@SerializedName("data")
	public EmailVerificationList list;
}
