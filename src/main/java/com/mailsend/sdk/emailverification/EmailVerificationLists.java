package com.mailsend.sdk.emailverification;

import com.google.gson.annotations.SerializedName;
import com.mailersend.sdk.util.PaginatedResponse;

public class EmailVerificationLists extends PaginatedResponse {

	@SerializedName("data")
	public EmailVerificationList[] lists;
}
