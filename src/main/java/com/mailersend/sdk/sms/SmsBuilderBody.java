/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.sms;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

/**
 * <p>SmsBuilderBody class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class SmsBuilderBody {

	@SerializedName("from")
	public String from;
	
	@SerializedName("to")
	public ArrayList<String> to = new ArrayList<String>();
	
	@SerializedName("text")
	public String text;
	
	@SerializedName("personalization")
	public ArrayList<SmsPersonalization> personalization = new ArrayList<SmsPersonalization>();
}
