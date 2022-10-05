package messages;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

import com.google.gson.annotations.SerializedName;
import com.mailersend.sdk.sms.SmsInfo;

/**
 * <p>SmsMessage class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class SmsMessage {

	@SerializedName("id")
	public String id;
	
	@SerializedName("from")
	public String from;
	
	@SerializedName("to")
	public String[] to;
	
	@SerializedName("text")
	public String text;
	
	@SerializedName("paused")
	public boolean paused;
	
	@SerializedName("created_at")
	private String createdAtStr;
	
	public Date createdAt;
	
	@SerializedName("sms")
	public SmsInfo sms;
	
	/**
	 * <p>postDeserialize.</p>
	 */
	public void postDeserialize() {
		if (createdAtStr != null && !createdAtStr.isBlank()) {
			
	        TemporalAccessor ta;
	        Instant instant;
	        
            ta = DateTimeFormatter.ISO_INSTANT.parse(createdAtStr);
            instant = Instant.from(ta);
            createdAt = Date.from(instant);
		}
	}
}
