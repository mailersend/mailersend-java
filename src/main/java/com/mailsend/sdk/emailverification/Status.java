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
 * <p>Status class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class Status {

	@SerializedName("name")
	public String name;
	
	@SerializedName("count")
	public int count;
}
