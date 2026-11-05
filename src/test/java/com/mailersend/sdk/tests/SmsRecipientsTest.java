package com.mailersend.sdk.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.time.Instant;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.sms.recipients.SmsRecipient;
import com.mailersend.sdk.sms.recipients.SmsRecipientList;
import com.mailersend.sdk.vcr.VcrRecorder;

public class SmsRecipientsTest {

	@BeforeEach
	public void setupEach(TestInfo info) throws IOException
	{
		VcrRecorder.useRecording("SmsRecipientsTest_" + info.getDisplayName());
	}
	
	@AfterEach
	public void afterEach() throws IOException
	{
		VcrRecorder.stopRecording();
	}
	

    @Test
    public void testSmsRecipientsRetrieval() {
        
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);
        
        try {
        
        	SmsRecipientList list = ms.sms().recipients().getRecipients();
        	
        	for (SmsRecipient recipient : list.recipients) {
        		
        		System.out.println(recipient.id);
        	}
        	
        } catch (MailerSendException ex) {
        	fail();
        }
    }
    
    
    @Test
    public void testSmsSingleRecipientRetrieval() {
        
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);
        
        try {
        
        	SmsRecipientList list = ms.sms().recipients().getRecipients();
        	
        	SmsRecipient recipient = ms.sms().recipients().getRecipient(list.recipients[0].id);
        	
        	System.out.println(recipient.id);
        	
        } catch (MailerSendException ex) {
        	fail();
        }
    }
    
    @Test
    public void testSmsRecipientUpdate() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

        	SmsRecipientList list = ms.sms().recipients().getRecipients();

        	SmsRecipient recipient = ms.sms().recipients().updateRecipient(list.recipients[0].id, "opt_out");

        	assertEquals("opt_out", recipient.status);

        } catch (MailerSendException ex) {
        	fail();
        }
    }

    /**
     * Tests that the status filter is included in the request URL when set
     */
    @Test
    public void testSmsRecipientsWithStatusFilter() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

        	SmsRecipientList list = ms.sms().recipients()
        	        .status("opt_out")
        	        .getRecipients();

        	assertEquals("opt_out", list.recipients[0].status);

        } catch (MailerSendException ex) {
        	fail();
        }
    }

    /**
     * Tests that the sms_number_id filter is included in the request URL when set
     */
    @Test
    public void testSmsRecipientsWithSmsNumberIdFilter() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

        	SmsRecipientList list = ms.sms().recipients()
        	        .numberId(TestHelper.smsPhoneNumberId)
        	        .getRecipients();

        	assertNotNull(list.recipients);

        } catch (MailerSendException ex) {
        	fail();
        }
    }


    /**
     * Tests that the opt out date and source of an SMS recipient are read
     */
    @Test
    public void testSmsRecipientReadsOptOut() throws MailerSendException {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        CapturingHttpClient.install(200, "{\"data\":{\"id\":\"627e7503d30078fb2208cc83\",\"number\":\"+1234567890\",\"status\":\"opt_out\","
            + "\"opted_out_at\":\"2022-01-02T12:00:00.000000Z\",\"opt_out_source\":\"api\",\"created_at\":\"2022-05-13T15:10:59.444000Z\"}}");

        SmsRecipient recipient = ms.sms().recipients().getRecipient("627e7503d30078fb2208cc83");

        assertEquals("api", recipient.optOutSource);
        assertEquals(Instant.parse("2022-01-02T12:00:00Z"), recipient.optedOutAt.toInstant());
    }
}
