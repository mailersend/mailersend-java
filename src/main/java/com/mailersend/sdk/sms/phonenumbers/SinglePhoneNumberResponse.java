package com.mailersend.sdk.sms.phonenumbers;

import com.google.gson.annotations.SerializedName;
import com.mailersend.sdk.MailerSendResponse;

public class SinglePhoneNumberResponse extends MailerSendResponse {

	@SerializedName("data")
	public PhoneNumber phoneNumber;
}
