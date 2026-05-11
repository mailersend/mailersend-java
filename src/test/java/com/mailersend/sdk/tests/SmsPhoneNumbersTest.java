package com.mailersend.sdk.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
    public void testPhoneNumbersRetrieval() {

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
    public void testSinglePhoneNumberRetrieval() {

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

    /**
     * Tests that a phone number can be updated (paused=true) and the response reflects the change
     */
    @Test
    public void testUpdatePhoneNumber() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            PhoneNumber number = ms.sms().phoneNumbers().updatePhoneNumber(
                    TestHelper.smsPhoneNumberId, true);

            assertNotNull(number);
            assertEquals(TestHelper.smsPhoneNumberId, number.id);
            assertTrue(number.paused);

        } catch (MailerSendException e) {

            fail();
        }
    }

    /**
     * Tests that a phone number can be deleted and returns true on success
     */
    @Test
    public void testDeletePhoneNumber() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            boolean result = ms.sms().phoneNumbers().deletePhoneNumber(TestHelper.smsPhoneNumberId);

            assertTrue(result);

        } catch (MailerSendException e) {

            fail();
        }
    }

    /**
     * Tests that the paused filter is included in the request URL when set
     */
    @Test
    public void testPhoneNumbersWithPausedFilter() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            PhoneNumberList numbers = ms.sms().phoneNumbers()
                    .paused(true)
                    .getPhoneNumbers();

            assertNotNull(numbers.phoneNumbers);
            assertTrue(numbers.phoneNumbers[0].paused);

        } catch (MailerSendException e) {

            fail();
        }
    }
}
