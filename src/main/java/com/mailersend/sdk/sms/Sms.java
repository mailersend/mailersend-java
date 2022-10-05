/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.sms;

import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.sms.activities.SmsActivities;
import com.mailersend.sdk.sms.inboundroutes.SmsInboundRoutes;
import com.mailersend.sdk.sms.messages.SmsMessages;
import com.mailersend.sdk.sms.phonenumbers.PhoneNumbers;
import com.mailersend.sdk.sms.recipients.SmsRecipients;
import com.mailersend.sms.webhooks.SmsWebhooks;

/**
 * <p>Sms class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class Sms {

	private MailerSend apiObjectReference;
	
	private SmsActivities activities;
	private SmsMessages messages;
	private PhoneNumbers phoneNumbers;
	private SmsRecipients recipients;
	private SmsInboundRoutes inboundRoutes;
	private SmsWebhooks webhooks;
	private SmsBuilder builder;
	
	
	/**
	 * <p>Constructor for Sms.</p>
	 *
	 * @param ref a {@link com.mailersend.sdk.MailerSend} object.
	 */
	public Sms(MailerSend ref) {
		apiObjectReference = ref;
		
		activities = new SmsActivities(ref);
		messages = new SmsMessages(ref);
		phoneNumbers = new PhoneNumbers(ref);
		recipients = new SmsRecipients(ref);
		inboundRoutes = new SmsInboundRoutes(ref);
		webhooks = new SmsWebhooks(ref);
		builder = new SmsBuilder(ref);
	}
	
	/**
	 * <p>activities.</p>
	 *
	 * @return a {@link com.mailersend.sdk.sms.activities.SmsActivities} object.
	 */
	public SmsActivities activities() {
		return activities;
	}
	
	/**
	 * <p>messages.</p>
	 *
	 * @return a {@link com.mailersend.sdk.sms.messages.SmsMessages} object.
	 */
	public SmsMessages messages() {
		return messages;
	}
	
	/**
	 * <p>phoneNumbers.</p>
	 *
	 * @return a {@link com.mailersend.sdk.sms.phonenumbers.PhoneNumbers} object.
	 */
	public PhoneNumbers phoneNumbers() {
		return phoneNumbers;
	}
	
	/**
	 * <p>recipients.</p>
	 *
	 * @return a {@link com.mailersend.sdk.sms.recipients.SmsRecipients} object.
	 */
	public SmsRecipients recipients() {
		return recipients;
	}
	
	/**
	 * <p>inboundRoutes.</p>
	 *
	 * @return a {@link com.mailersend.sdk.sms.inboundroutes.SmsInboundRoutes} object.
	 */
	public SmsInboundRoutes inboundRoutes() {
		return inboundRoutes;
	}
	
	/**
	 * <p>webhooks.</p>
	 *
	 * @return a {@link com.mailersend.sms.webhooks.SmsWebhooks} object.
	 */
	public SmsWebhooks webhooks() {
		return webhooks;
	}
	
	/**
	 * <p>builder.</p>
	 *
	 * @return a {@link com.mailersend.sdk.sms.SmsBuilder} object.
	 */
	public SmsBuilder builder() {
		return builder;
	}
}
