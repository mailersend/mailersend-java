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
 * <p>VerificationResult class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class VerificationResult {

	@SerializedName("address")
	public String address;
	
	@SerializedName("result")
	public String result;
}
