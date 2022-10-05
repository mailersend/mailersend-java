package com.mailersend.sdk.scheduledmessages;

import com.google.gson.annotations.SerializedName;
import com.mailersend.sdk.util.PaginatedResponse;

/**
 * <p>ScheduledMessagesList class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class ScheduledMessagesList extends PaginatedResponse {

	@SerializedName("data")
	public ScheduledMessage[] scheduledMessages;
}
