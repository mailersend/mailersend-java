package com.mailersend.sdk.sms.inboundroutes;

import com.google.gson.annotations.SerializedName;

public class SmsInboundRouteBuilderBody {

	@SerializedName("sms_number_id")
	public String smsNumberId;
	
	@SerializedName("name")
	public String name;
	
	@SerializedName("filter")
	public Filter filter;
	
	@SerializedName("forward_url")
	public String forwardUrl;
	
	@SerializedName("enabled")
	public boolean enabled;
}
