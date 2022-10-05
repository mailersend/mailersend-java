package com.mailersend.sdk.sms.inboundroutes;

import com.google.gson.annotations.SerializedName;

public class Filter {

	@SerializedName("value")
	public String value;
	
	@SerializedName("comparer")
	public String comparer;
}
