package com.mailersend.sms.webhooks;

import com.google.gson.annotations.SerializedName;
import com.mailersend.sdk.MailerSendResponse;

class SingleSmsWebhookResponse extends MailerSendResponse {

	@SerializedName("data")
	public SmsWebhook webhook;
}
