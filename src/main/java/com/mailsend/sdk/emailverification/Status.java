package com.mailsend.sdk.emailverification;

import com.google.gson.annotations.SerializedName;

public class Status {

	@SerializedName("name")
	public String name;
	
	@SerializedName("count")
	public int count;
}
