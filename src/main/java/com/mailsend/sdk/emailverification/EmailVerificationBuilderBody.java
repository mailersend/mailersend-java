/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailsend.sdk.emailverification;

import com.google.gson.annotations.SerializedName;

/**
 * <p>EmailVerificationBuilderBody class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class EmailVerificationBuilderBody {

	@SerializedName("name")
	public String name;

	@SerializedName("list_id")
	public String listId;

	@SerializedName("emails")
	public String[] emails;

	@SerializedName("verify")
	public Boolean verify;

	/**
	 * <p>reset.</p>
	 */
	public void reset() {
		name = null;
		listId = null;
		emails = null;
		verify = null;
	}
}
