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
 * <p>ListVerificationResults class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class ListVerificationResults extends PaginatedResponse {

	@SerializedName("data")
	public VerificationResult[] results;
}
