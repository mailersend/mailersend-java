/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.emailverification;

import com.google.gson.annotations.SerializedName;
import com.mailersend.sdk.util.ResponseLinks;

/**
 * The paginated {@code emails} object of a list requested with {@code detailed=true}.
 *
 * @author mailersend
 */
public class EmailVerificationListEmails {

	@SerializedName("data")
	public VerificationResult[] results;

	@SerializedName("pagination")
	public EmailVerificationListPagination pagination;

	@SerializedName("links")
	public ResponseLinks links;
}
