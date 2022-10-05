package com.mailsend.sdk.emailverification;

import com.google.gson.annotations.SerializedName;

public class VerificationResult {

	@SerializedName("address")
	public String address;
	
	@SerializedName("result")
	public String result;
}
