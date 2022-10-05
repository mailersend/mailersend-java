package com.mailersend.sms.webhooks;

import com.google.gson.annotations.SerializedName;
import com.mailersend.sdk.util.PaginatedResponse;

public class SmsWebhookList extends PaginatedResponse {
	
	@SerializedName("data")
	public SmsWebhook[] webhooks;
	
	protected void postDeserialize() {
		
		for (SmsWebhook webhook : webhooks) {
			
			webhook.postDeserialize();
		}
	}
}
