package com.mailsend.sdk.emailverification;

import com.google.gson.annotations.SerializedName;
import com.mailersend.sdk.MailerSendResponse;

public class SingleEmailVerificationListResponse extends MailerSendResponse {

	@SerializedName("data")
	public EmailVerificationList list;
}
