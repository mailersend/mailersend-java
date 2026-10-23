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
        	
        	assertEquals("627e7503d30078fb2208cc83", list.recipients[0].id);
        	assertEquals(Instant.parse("2022-05-13T15:10:59.444Z"), list.recipients[0].createdAt.toInstant());
        	
        } catch (MailerSendException ex) {
        	fail();
        }
    }
    
    
    /**
     * Tests that page and limit are sent
     */
    @Test
    public void testSmsRecipientsWithPagination() {
        
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);
        
        try {
        
        	SmsRecipientList list = ms.sms().recipients()
        	        .page(2)
        	        .limit(10)
        	        .getRecipients();
        	
        	assertEquals("627e7503d30078fb2208cc83", list.recipients[0].id);
        	
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
        	
        	assertEquals(list.recipients[0].id, recipient.id);
        	
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
        	assertEquals("api", recipient.optOutSource);
        	assertEquals(Instant.parse("2022-01-02T12:00:00Z"), recipient.optedOutAt.toInstant());

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
}
