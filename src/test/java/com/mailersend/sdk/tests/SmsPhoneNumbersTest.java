package com.mailersend.sdk.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.sms.phonenumbers.PhoneNumber;
import com.mailersend.sdk.sms.phonenumbers.PhoneNumberList;
import com.mailersend.sdk.vcr.VcrRecorder;

public class SmsPhoneNumbersTest {

	@BeforeEach
	public void setupEach(TestInfo info) throws IOException
	{
		VcrRecorder.useRecording("SmsPhoneNumbersTest_" + info.getDisplayName());
	}
	
	@AfterEach
	public void afterEach() throws IOException
	{
		VcrRecorder.stopRecording();
	}
	
    /**
     * Gets a lists of phone numbers
     */
    @Test
    public void TestPhoneNumbersRetrieval() {
        
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);
        
        try {
            
            PhoneNumberList numbers = ms.sms().phoneNumbers().getPhoneNumbers();
            
            for (PhoneNumber number : numbers.phoneNumbers) {
            	
            	System.out.println(number.id);
            }

        } catch (MailerSendException e) {
            
            fail();
        }
    }
    
    /**
     * Gets a single phone number
     */
    @Test
    public void TestSinglePhoneNumberRetrieval() {
        
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);
        
        try {
            
            PhoneNumberList numbers = ms.sms().phoneNumbers().getPhoneNumbers();
            
            String phoneNumberId = numbers.phoneNumbers[0].id;
            
            PhoneNumber number = ms.sms().phoneNumbers().getPhoneNumber(phoneNumberId);
            
            assertEquals(phoneNumberId, number.id);

        } catch (MailerSendException e) {
            
            fail();
        }
    }
}
