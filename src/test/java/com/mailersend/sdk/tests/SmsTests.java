package com.mailersend.sdk.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.sms.phonenumbers.PhoneNumberList;
import com.mailersend.sdk.vcr.VcrRecorder;

public class SmsTests {

	private static final String smsFrom = "+18133657349";

	@BeforeEach
	public void setupEach(TestInfo info) throws IOException
	{
		VcrRecorder.useRecording("SmsTests_" + info.getDisplayName());
	}
	
	
	@AfterEach
	public void afterEach() throws IOException
	{
		VcrRecorder.stopRecording();
	}
	
	
	@Test
	public void testSendSms() {
		
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);
        
        try {
        	
        	PhoneNumberList numbers = ms.sms().phoneNumbers().getPhoneNumbers();
        	
        	String messageId = ms.sms().builder().from(numbers.phoneNumbers[0].telephoneNumber)
        	.addRecipient(numbers.phoneNumbers[0].telephoneNumber)
        	.text("test sms {{name}}")
        	.addPersonalization(numbers.phoneNumbers[0].telephoneNumber, "name", "name personalization")
        	.send();
        	
        	assertEquals("6321a3c63bed5c70530b8136", messageId);
        
        } catch (MailerSendException e) {
            
            fail();
        }
	}
	
	
	/**
	 * Tests that personalization values for the same recipient are merged into one entry
	 */
	@Test
	public void testSendSmsMergesPersonalizationForSameRecipient() {
		
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);
        
        try {
        	
        	String messageId = ms.sms().builder().from(smsFrom)
        	.addRecipient(smsFrom)
        	.text("test sms {{name}} {{code}}")
        	.addPersonalization(smsFrom, "name", "name personalization")
        	.addPersonalization(smsFrom, "code", "1234")
        	.send();
        	
        	assertEquals("6321a3c63bed5c70530b8137", messageId);
        
        } catch (MailerSendException e) {
            
            fail();
        }
	}
	
	
	/**
	 * Tests that send() returns null when the response has no x-sms-message-id header
	 */
	@Test
	public void testSendSmsWithoutMessageIdHeader() {
		
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);
        
        try {
        	
        	String messageId = ms.sms().builder().from(smsFrom)
        	.addRecipient(smsFrom)
        	.text("test sms")
        	.send();
        	
        	assertNull(messageId);
        
        } catch (MailerSendException e) {
            
            fail();
        }
	}
}
