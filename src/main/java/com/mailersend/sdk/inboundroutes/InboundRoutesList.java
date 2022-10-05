package com.mailersend.sdk.inboundroutes;

import com.google.gson.annotations.SerializedName;
import com.mailersend.sdk.util.PaginatedResponse;

public class InboundRoutesList extends PaginatedResponse {

	@SerializedName("data")
	public InboundRoute[] routes;
}
