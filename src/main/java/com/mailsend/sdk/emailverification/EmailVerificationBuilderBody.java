package com.mailsend.sdk.emailverification;

import com.google.gson.annotations.SerializedName;

public class EmailVerificationBuilderBody {

	@SerializedName("name")
	public String name;
	
	@SerializedName("emails")
	public String[] emails;
	
	public void reset() {
		name = null;
		emails = null;
	}
}
