/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailsend.sdk.emailverification;

import com.google.gson.annotations.SerializedName;
import com.mailersend.sdk.util.PaginatedResponse;

/**
 * <p>EmailVerificationLists class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class EmailVerificationLists extends PaginatedResponse {

	@SerializedName("data")
	public EmailVerificationList[] lists;
}
