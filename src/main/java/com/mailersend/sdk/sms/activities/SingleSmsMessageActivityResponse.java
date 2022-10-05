package com.mailersend.sdk.sms.activities;

import com.google.gson.annotations.SerializedName;
import com.mailersend.sdk.MailerSendResponse;

public class SingleSmsMessageActivityResponse extends MailerSendResponse {

	@SerializedName("data")
	public SmsMessageActivity activity;
}
