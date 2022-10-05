package com.mailersend.sdk.scheduledmessages;

import com.google.gson.annotations.SerializedName;
import com.mailersend.sdk.util.PaginatedResponse;

public class ScheduledMessagesList extends PaginatedResponse {

	@SerializedName("data")
	public ScheduledMessage[] scheduledMessages;
}
