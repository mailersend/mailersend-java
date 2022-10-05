package com.mailersend.sdk.tests;

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
	public void TestSendSms() {
		
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);
        
        try {
        	
        	PhoneNumberList numbers = ms.sms().phoneNumbers().getPhoneNumbers();
        	
        	String messageId = ms.sms().builder().from(numbers.phoneNumbers[0].telephoneNumber)
        	.addRecipient(numbers.phoneNumbers[0].telephoneNumber)
        	.text("test sms {{name}}")
        	.addPersonalization(numbers.phoneNumbers[0].telephoneNumber, "name", "name personalization")
        	.send();
        	
        	System.out.println(messageId);
        
        } catch (MailerSendException e) {
            
            fail();
        }
	}
}
