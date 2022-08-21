package com.mailersend.sdk.sms.phonenumbers;

import com.google.gson.annotations.SerializedName;
import com.mailersend.sdk.util.PaginatedResponse;

public class PhoneNumberList extends PaginatedResponse {

	@SerializedName("data")
	public PhoneNumber[] phoneNumbers;
	
	public void postDeserialize() {
		for (PhoneNumber n : phoneNumbers) {
			n.postDeserialize();
		}
	}
}
