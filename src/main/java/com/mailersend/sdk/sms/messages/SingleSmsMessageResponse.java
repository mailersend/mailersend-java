package com.mailersend.sdk.sms.messages;

import com.google.gson.annotations.SerializedName;
import com.mailersend.sdk.MailerSendResponse;

public class SingleSmsMessageResponse extends MailerSendResponse {

	@SerializedName("data")
	public SmsMessage message;
}
