/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.sms.inboundroutes;

import com.google.gson.annotations.SerializedName;

/**
 * <p>Filter class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class Filter {

	@SerializedName("value")
	public String value;
	
	@SerializedName("comparer")
	public String comparer;
}
