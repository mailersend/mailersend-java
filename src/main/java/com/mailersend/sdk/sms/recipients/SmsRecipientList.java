package com.mailersend.sdk.sms.recipients;

import com.google.gson.annotations.SerializedName;
import com.mailersend.sdk.util.PaginatedResponse;

public class SmsRecipientList extends PaginatedResponse {

	@SerializedName("data")
	public SmsRecipient[] recipients;
	
	public void postDeserialize() {
		for (SmsRecipient r : recipients) {
			r.postDeserialize();
		}
	}
}
