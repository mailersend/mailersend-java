package messages;

import com.google.gson.annotations.SerializedName;
import com.mailersend.sdk.MailerSendResponse;

/**
 * <p>SingleSmsMessageResponse class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class SingleSmsMessageResponse extends MailerSendResponse {

	@SerializedName("data")
	public SmsMessage message;
}
