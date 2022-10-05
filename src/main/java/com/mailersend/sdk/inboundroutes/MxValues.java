package com.mailersend.sdk.inboundroutes;

import com.google.gson.annotations.SerializedName;

public class MxValues {

	@SerializedName("priority")
	public int priority;
	
	@SerializedName("target")
	public String target;
	
	public MxValues() {
		// intentionally left blank
	}
	
	public MxValues(int priority, String target) {
		this.priority = priority;
		this.target = target;
	}
}
