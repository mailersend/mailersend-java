package com.mailersend.sdk.sms;

import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.sms.activities.SmsActivities;
import com.mailersend.sdk.sms.messages.SmsMessages;
import com.mailersend.sdk.sms.phonenumbers.PhoneNumbers;
import com.mailersend.sdk.sms.recipients.SmsRecipients;

public class Sms {

	private MailerSend apiObjectReference;
	
	private SmsActivities activities;
	private SmsMessages messages;
	private PhoneNumbers phoneNumbers;
	private SmsRecipients recipients;
	
	public Sms(MailerSend ref) {
		apiObjectReference = ref;
		
		activities = new SmsActivities(ref);
		messages = new SmsMessages(ref);
		phoneNumbers = new PhoneNumbers(ref);
		recipients = new SmsRecipients(ref);
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
}
