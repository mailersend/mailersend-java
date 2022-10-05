package com.mailersend.sdk.inboundroutes;

import com.google.gson.annotations.SerializedName;
import com.mailersend.sdk.MailerSendResponse;

public class SingleInboundRouteResponse extends MailerSendResponse {

	@SerializedName("data")
	public InboundRoute route;
}
