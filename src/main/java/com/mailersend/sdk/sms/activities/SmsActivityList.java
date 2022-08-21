package com.mailersend.sdk.sms.activities;

import com.google.gson.annotations.SerializedName;
import com.mailersend.sdk.util.PaginatedResponse;

public class SmsActivityList extends PaginatedResponse {

	@SerializedName("data")
	public SmsActivity[] smsActivities;
	
	public void postDeserialize() {
		for (SmsActivity a : smsActivities) {
			a.postDeserialize();
		}
	}
}
