package com.mailersend.sdk.sms.inboundroutes;

import com.google.gson.annotations.SerializedName;
import com.mailersend.sdk.util.PaginatedResponse;

public class SmsInboundRouteList extends PaginatedResponse {

	@SerializedName("data")
	public SmsInboundRoute[] routes;
	
	public void postDeserialize() {
		
		for (SmsInboundRoute route : routes) {
			
			route.postDeserialize();
		}
	}
}
