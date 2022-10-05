/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.sms.inboundroutes;

import com.google.gson.annotations.SerializedName;
import com.mailersend.sdk.util.PaginatedResponse;

/**
 * <p>SmsInboundRouteList class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class SmsInboundRouteList extends PaginatedResponse {

	@SerializedName("data")
	public SmsInboundRoute[] routes;
	
	/**
	 * <p>postDeserialize.</p>
	 */
	public void postDeserialize() {
		
		for (SmsInboundRoute route : routes) {
			
			route.postDeserialize();
		}
	}
}
