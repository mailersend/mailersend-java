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
	
	@SerializedName("emails")
	public String[] emails;
	
	/**
	 * <p>reset.</p>
	 */
	public void reset() {
		name = null;
		emails = null;
	}
}
