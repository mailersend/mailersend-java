package com.mailersend.sdk.scheduledmessages;

import com.google.gson.annotations.SerializedName;
import com.mailersend.sdk.MailerSendResponse;

public class SingleScheduledMessageResponse extends MailerSendResponse {

	@SerializedName("data")
	public ScheduledMessage scheduledMessage;
}
