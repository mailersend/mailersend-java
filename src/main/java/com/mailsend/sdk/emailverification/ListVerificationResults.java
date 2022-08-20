package com.mailsend.sdk.emailverification;

import com.google.gson.annotations.SerializedName;
import com.mailersend.sdk.util.PaginatedResponse;

public class ListVerificationResults extends PaginatedResponse {

	@SerializedName("data")
	public VerificationResult[] results;
}
