package com.mailersend.sdk.sms.recipients;

import com.google.gson.annotations.SerializedName;
import com.mailersend.sdk.MailerSendResponse;

public class SingleSmsRecipientResponse extends MailerSendResponse {

	@SerializedName("data")
	public SmsRecipient recipient;
}
