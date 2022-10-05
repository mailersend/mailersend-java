/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.sms.phonenumbers;

import com.google.gson.annotations.SerializedName;
import com.mailersend.sdk.util.PaginatedResponse;

/**
 * <p>PhoneNumberList class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class PhoneNumberList extends PaginatedResponse {

	@SerializedName("data")
	public PhoneNumber[] phoneNumbers;
	
	/**
	 * <p>postDeserialize.</p>
	 */
	public void postDeserialize() {
		for (PhoneNumber n : phoneNumbers) {
			n.postDeserialize();
		}
	}
}
