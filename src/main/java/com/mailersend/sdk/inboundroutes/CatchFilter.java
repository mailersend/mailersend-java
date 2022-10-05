package com.mailersend.sdk.inboundroutes;

import com.google.gson.annotations.SerializedName;

public class CatchFilter {

	@SerializedName("type")
	public String type;
	
	@SerializedName("filters")
	public Filter[] filters;
	
	public CatchFilter() {
		// intentionally left blank
	}
	
	public CatchFilter(String type, Filter[] filters) {
		this.type = type;
		this.filters = filters;
	}
}
