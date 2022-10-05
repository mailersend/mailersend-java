package com.mailersend.sdk.scheduledmessages;

import com.google.gson.annotations.SerializedName;
import com.mailersend.sdk.MailerSendResponse;

/**
 * <p>SingleScheduledMessageResponse class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class SingleScheduledMessageResponse extends MailerSendResponse {

	@SerializedName("data")
	public ScheduledMessage scheduledMessage;
}
