package com.mailersend.sdk.sms.messages;

import com.google.gson.annotations.SerializedName;
import com.mailersend.sdk.util.PaginatedResponse;

public class SmsMessageList extends PaginatedResponse {

	@SerializedName("data")
	public SmsMessage[] messages;
	
	public void postDeserialize() {
		for (SmsMessage m : messages) {
			m.postDeserialize();
		}
	}
}
