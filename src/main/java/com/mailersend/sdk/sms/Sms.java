package com.mailersend.sdk.sms;

import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.sms.activities.SmsActivities;
import com.mailersend.sdk.sms.inboundroutes.SmsInboundRoutes;
import com.mailersend.sdk.sms.messages.SmsMessages;
import com.mailersend.sdk.sms.phonenumbers.PhoneNumbers;
import com.mailersend.sdk.sms.recipients.SmsRecipients;
import com.mailersend.sms.webhooks.SmsWebhooks;

public class Sms {

	private MailerSend apiObjectReference;
	
	private SmsActivities activities;
	private SmsMessages messages;
	private PhoneNumbers phoneNumbers;
	private SmsRecipients recipients;
	private SmsInboundRoutes inboundRoutes;
	private SmsWebhooks webhooks;
	private SmsBuilder builder;
	
	
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
	
	public SmsActivities activities() {
		return activities;
	}
	
	public SmsMessages messages() {
		return messages;
	}
	
	public PhoneNumbers phoneNumbers() {
		return phoneNumbers;
	}
	
	public SmsRecipients recipients() {
		return recipients;
	}
	
	public SmsInboundRoutes inboundRoutes() {
		return inboundRoutes;
	}
	
	public SmsWebhooks webhooks() {
		return webhooks;
	}
	
	public SmsBuilder builder() {
		return builder;
	}
}
