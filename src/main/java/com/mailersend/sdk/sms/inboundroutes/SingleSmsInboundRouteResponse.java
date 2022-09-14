package com.mailersend.sdk.sms.inboundroutes;

import com.google.gson.annotations.SerializedName;
import com.mailersend.sdk.MailerSendResponse;

class SingleSmsInboundRouteResponse extends MailerSendResponse {

	@SerializedName("data")
	public SmsInboundRoute route;
}
